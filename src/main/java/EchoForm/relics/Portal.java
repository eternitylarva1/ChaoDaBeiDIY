package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.monsters.exordium.GremlinFat;
import com.megacrit.cardcrawl.monsters.exordium.JawWorm;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class Portal extends CustomRelic {
    public static final String ID = "echoForm:Portal";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/Portal.png";

    public Portal() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    public void atBattleStart() {
        AbstractRoom room=AbstractDungeon.getCurrRoom();
        if (room instanceof MonsterRoomElite) {
            this.flash();

            // 清空现有的怪物列表
            room.monsters= new MonsterGroup(new AbstractMonster[]{new JawWorm(-490.0F, -5.0F, true)});



            // 初始化怪物
            room.monsters.init();

        }

    }


    @Override
    public void onEnterRoom(com.megacrit.cardcrawl.rooms.AbstractRoom room) {
        /*
        // 所有精英房间变为一个大颚虫
        if (room instanceof MonsterRoomElite) {
            this.flash();
            
            // 清空现有的怪物列表
            room.monsters.monsters.clear();
            
            // 创建3个大颚虫
            for (int i = 0; i < 3; i++) {
                GremlinFat gremlinFat = new GremlinFat(-50.0F + i * 250.0F, 10.0F);
                room.monsters.add(gremlinFat);
            }
            
            // 初始化怪物
            room.monsters.init();
        }*/
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Portal();
    }
}
