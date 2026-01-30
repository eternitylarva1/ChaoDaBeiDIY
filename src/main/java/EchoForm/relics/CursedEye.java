package EchoForm.relics;

import basemod.AutoAdd;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
@AutoAdd.Ignore
public class CursedEye extends CustomRelic {
    public static final String ID = "echoForm:CursedEye";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/CursedEye.png";
    
    private boolean strikePlayedThisTurn = false;

    public CursedEye() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] ;
    }

    @Override
    public void atBattleStart() {
        this.strikePlayedThisTurn = false;
    }

    @Override
    public void atTurnStart() {
        this.strikePlayedThisTurn = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 检查是否是打击牌
        if (card.cardID.equals("Strike")) {
            this.strikePlayedThisTurn = true;
            // 打击额外造成2次伤害
            if (m != null && !m.isDeadOrEscaped()) {
                this.flash();
                // 额外造成2次伤害
                addToBot((AbstractGameAction)new DamageAction(m, new DamageInfo(AbstractDungeon.player, card.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot((AbstractGameAction)new DamageAction(m, new DamageInfo(AbstractDungeon.player, card.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // 如果回合中打出过打击，那么本回合你受伤时将被移动到随机房间
        if (this.strikePlayedThisTurn && damageAmount > 0 && info.owner != null && info.owner != AbstractDungeon.player) {
            this.flash();
            // 移动到随机房间（不会移动到已经通过的层）
            teleportToRandomRoom();
        }
        return damageAmount;
    }
    
    private void teleportToRandomRoom() {
        // 获取当前地图节点
        MapRoomNode currentNode = AbstractDungeon.currMapNode;
        
        // 获取所有可用的房间节点
        ArrayList<MapRoomNode> availableNodes = new ArrayList<>();
        
        // 遍历当前层的所有节点
        for (ArrayList<MapRoomNode> row : AbstractDungeon.map) {
            for (MapRoomNode node : row) {
                // 只考虑未访问过的节点，且不是当前节点
                if (!node.taken && node != currentNode && node.hasEdges()) {
                    availableNodes.add(node);
                }
            }
        }
        
        // 如果有可用的节点，随机选择一个
        if (!availableNodes.isEmpty()) {
            MapRoomNode targetNode = availableNodes.get(AbstractDungeon.cardRandomRng.random(availableNodes.size() - 1));
            
            // 设置当前节点为目标节点
            AbstractDungeon.setCurrMapNode(targetNode);
            
            // 开始房间转换
            AbstractDungeon.nextRoomTransitionStart();
            
            // 显示提示信息
            AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.combat.RoomTintEffect(
                new com.badlogic.gdx.graphics.Color(0.5f, 0f, 0.5f, 0.8f), 0.8f));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CursedEye();
    }
}