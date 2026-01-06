package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Spinach extends CustomRelic {
    public static final String ID = "echoForm:Spinach";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/Spinach.png";
    
    private int battlesRemaining = 5;
    private static final int MAX_HP_BONUS = 8;
    private static final int STRENGTH_BONUS = 3;

    public Spinach() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        // 拾取时获得8点血量上限
        AbstractDungeon.player.increaseMaxHp(MAX_HP_BONUS, true);
    }

    @Override
    public void atBattleStart() {
        // 之后的5场战斗开始时获得3点力量
        if (this.battlesRemaining > 0) {
            this.flash();
            addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, STRENGTH_BONUS), STRENGTH_BONUS));
            this.battlesRemaining--;
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Spinach();
    }
}