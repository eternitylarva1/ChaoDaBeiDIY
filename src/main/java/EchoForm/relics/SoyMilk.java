package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class SoyMilk extends CustomRelic {
    public static final String ID = "echoForm:SoyMilk";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/SoyMilk.png";
    
    private static final int DAMAGE_REDUCTION = 75;

    public SoyMilk() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.COMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        // 抽到攻击牌时使其耗能减一
        if (card.type == AbstractCard.CardType.ATTACK && card.cost > 0) {
            this.flash();
            card.modifyCostForCombat(-1);
        }
    }

    @Override
    public float atDamageModify(float damage, AbstractCard card) {
        // 你的伤害降低75%
        if (card.type == AbstractCard.CardType.ATTACK) {
            return damage * (1.0f - DAMAGE_REDUCTION / 100.0f);
        }
        return damage;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SoyMilk();
    }
}