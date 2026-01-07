package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class FoolWizardHat extends CustomRelic {
    public static final String ID = "echoForm:FoolWizardHat";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/FoolWizardHat.png";
    
    private boolean lastCardWasAttack = false;

    public FoolWizardHat() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.lastCardWasAttack = false;
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 你不能连续打出攻击牌
        this.lastCardWasAttack = (card.type == AbstractCard.CardType.ATTACK);
    }

    @Override
    public boolean canPlay(AbstractCard card) {
        // 你不能连续打出攻击牌
        if (this.lastCardWasAttack && card.type == AbstractCard.CardType.ATTACK) {
            return false;
        }
        return true;
    }

    @Override
    public void atTurnStart() {
        this.flash();
        this.lastCardWasAttack = false;
        // 每回合抽牌加2
        addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 2));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FoolWizardHat();
    }
}