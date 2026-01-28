package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PotionBelt;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import EchoForm.potions.MilkPotion;

public class MilkBucket extends CustomRelic {
    public static final String ID = "echoForm:MilkBucket";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/MilkBucket.png";

    public MilkBucket() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.SOLID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        // 拾起时，获得一个药水栏位
        AbstractDungeon.player.potionSlots++;
      AbstractDungeon.player.potions.add(new PotionSlot(AbstractDungeon.player.potionSlots - 1));

    }

    @Override
    public void onVictory() {
        // 战斗结束后额外掉落一瓶牛奶
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.getCurrRoom().addPotionToRewards(new MilkPotion());
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new MilkBucket();
    }
}
