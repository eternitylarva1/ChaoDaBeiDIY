package EchoForm.powers;

import EchoForm.utils.TextureUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class NeurotoxinPower extends AbstractPower {
    public static final String POWER_ID = "NeurotoxinPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    private static final String IMG_PATH_128 = "echoFormResources/images/powers/NeurotoxinPowerPower84.png";
    private static final String IMG_PATH_48 = "echoFormResources/images/powers/NeurotoxinPowerPower32.png";

    private static TextureAtlas.AtlasRegion region128;
    private static TextureAtlas.AtlasRegion region48;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        
        Texture tex = ImageMaster.loadImage(IMG_PATH_128);
        if (tex != null) {
            region128 = new TextureAtlas.AtlasRegion(tex, 0, 0, 84, 84);
            region48 = TextureUtils.resizeTexture(region128, 48, 48);
        }
    }

    public NeurotoxinPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img=ImageMaster.loadImage(IMG_PATH_48);
        this.region128 = region128;
        this.region48 = region48;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    // 当怪物准备攻击时，随机改变目标
    public void onMonsterAttack(AbstractMonster monster, AbstractCreature originalTarget) {
        if (monster.hasPower(PoisonPower.POWER_ID) && monster.hasPower(POWER_ID)) {
            // 获取所有可能的目标
            ArrayList<AbstractCreature> possibleTargets = new ArrayList<>();
            
            // 添加玩家
            possibleTargets.add(AbstractDungeon.player);
            
            // 添加其他怪物（除了自己）
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m != monster && !m.isDead && !m.isDying) {
                    possibleTargets.add(m);
                }
            }
            
            // 随机选择一个目标
            if (possibleTargets.size() > 1) {
                AbstractCreature newTarget = possibleTargets.get(AbstractDungeon.cardRandomRng.random(possibleTargets.size() - 1));
                
                // 如果新目标不是玩家，则显示提示
                if (newTarget != AbstractDungeon.player) {
                    // 显示目标改变的视觉效果
                    AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.combat.RoomTintEffect(
                        new com.badlogic.gdx.graphics.Color(0.0f, 1.0f, 0.0f, 0.3f), 0.5f));
                }
                
                // 修改怪物的攻击意图目标
                monster.intent = com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK;
                monster.setMove((byte) 0, com.megacrit.cardcrawl.monsters.AbstractMonster.Intent.ATTACK,
                               monster.getIntentDmg(), 0, newTarget instanceof AbstractMonster);
            }
        }
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 在怪物回合结束时，重置怪物的攻击意图
        if (!isPlayer) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.hasPower(POWER_ID)) {
                    m.createIntent();
                }
            }
        }
    }
}
