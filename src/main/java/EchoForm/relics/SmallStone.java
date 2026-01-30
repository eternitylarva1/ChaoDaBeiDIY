package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.ArrayList;

public class SmallStone extends CustomRelic {
    public static final String ID = "echoForm:SmallStone";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/SmallStone.png";
    
    // 计数器，用于跟踪房间数量

    // 标记是否已经触发了强制战斗
    private boolean forceBattleTriggered = false;

    public SmallStone() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.COMMON, LandingSound.SOLID);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        // 战斗开始时抽1张牌
        addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 1));
        // 获得1点力量
        addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 1), 1));
    }
    
    @Override
    public void onEnterRoom(AbstractRoom room) {

        this.counter++;
        if (this.counter % 4 == 0 && !forceBattleTriggered) {
            if (room instanceof com.megacrit.cardcrawl.rooms.RestRoom ||
                room instanceof com.megacrit.cardcrawl.rooms.ShopRoom ||
                room instanceof com.megacrit.cardcrawl.rooms.EventRoom) {
                
                // 将当前房间转换为战斗房间
                this.flash();
                forceBattleTriggered = true;
                
                // 获取当前地图节点
                MapRoomNode currentNode = AbstractDungeon.currMapNode;
                
                // 替换房间为战斗房间
                currentNode.room = new com.megacrit.cardcrawl.rooms.MonsterRoom();
                
                // 重置房间
                AbstractDungeon.nextRoomTransitionStart();
            }
        } else if (this.counter % 4 == 0 && room instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) {
            // 如果已经是战斗房间，则标记为已触发
            forceBattleTriggered = true;
        }
        
        // 重置标记，为下一个4房间周期做准备
        if (this.counter % 4 == 0) {
            forceBattleTriggered = false;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SmallStone();
    }
}