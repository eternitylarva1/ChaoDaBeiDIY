package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public  class PrimordialWater extends CustomRelic implements ClickableRelic {
    public static final String ID = "echoForm:PrimordialWater";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/PrimordialWater.png";
    
    private static final int MAX_MIRACLES = 7;

    public PrimordialWater() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + (MAX_MIRACLES) + DESCRIPTIONS[1];
    }

    @Override
    public void onTrigger() {
        // 你可点击此遗物，然后获得一张奇迹（每场战斗限6次）
        if (this.counter < MAX_MIRACLES && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            this.flash();
            this.counter++;
            this.description = getUpdatedDescription();
            this.tips.clear();
            this.tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(this.name, this.description));
            initializeTips();
            
            // 获得一张奇迹卡牌
            AbstractCard miracle = new Miracle();
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(miracle, 1));
        }
    }

    @Override
    public boolean checkTrigger() {
        // 检查是否可以触发
        return this.counter < MAX_MIRACLES && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    @Override
    public void onEnterRoom(AbstractRoom room) {
        // 进入新房间时重置计数
        this.counter = 0;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public void onVictory() {
        // 战斗胜利时重置计数
        this.counter = 0;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new com.megacrit.cardcrawl.helpers.PowerTip(this.name, this.description));
        initializeTips();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PrimordialWater();
    }

    @Override
    public void onRightClick() {
        this.onTrigger();
    }
}
