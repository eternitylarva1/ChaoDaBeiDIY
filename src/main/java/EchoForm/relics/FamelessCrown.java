package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FamelessCrown extends CustomRelic {
    public static final String ID = "echoForm:FamelessCrown";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/FamelessCrown.png";
    
    public FamelessCrown() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] ;
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.counter = AbstractDungeon.player.energy.energyMaster+3;
    }

    @Override
    public void atTurnStart() {

        this.flash();
        // 每回合抽牌加1
        addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 1));
        // 能量加3，每回合获得能量逐渐减少，可以倒扣，倒扣数量不会超过EnergyPanel.totalCount-1
        // 确保倒扣数量不会超过当前总能量-1（至少保留1点能量）
        int maxEnergy = AbstractDungeon.player.energy.energyMaster;

        addToBot((AbstractGameAction)new GainEnergyAction(this.counter-maxEnergy));
        if (this.counter>=2) {
            this.counter--;
        }

        // 如果counter小于最小值，则重置为3

    }

    @Override
    public AbstractRelic makeCopy() {
        return new FamelessCrown();
    }
}