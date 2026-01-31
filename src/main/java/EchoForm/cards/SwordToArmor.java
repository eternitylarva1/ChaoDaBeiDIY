package EchoForm.cards;

import EchoForm.helpers.ModHelper;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import EchoForm.actions.ExhaustAllAttackAction;

@AutoAdd.Ignore

public class SwordToArmor extends AbstractCard {
    public static final String ID = ModHelper.makePath("SwordToArmor");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    private static final int COST = 2;
    private static final int PLATED_ARMOR_PER_CARD = 2;
    
    public SwordToArmor() {
        super(ID, NAME, "red/skill/swordToArmor", COST, DESCRIPTION, 
              CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        
        this.baseMagicNumber = PLATED_ARMOR_PER_CARD;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new ExhaustAllAttackAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SwordToArmor();
    }
}