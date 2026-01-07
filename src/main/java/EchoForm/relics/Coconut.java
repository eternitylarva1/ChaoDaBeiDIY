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
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Coconut extends CustomRelic {
    public static final String ID = "echoForm:Coconut";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/Coconut.png";
    
    private static final int METALLICIZE_AMOUNT = 8;
    private static final float MAX_HP_REDUCTION = 0.2f;

    public Coconut() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        // 拾取时，最大生命降低20%
        AbstractPlayer p = AbstractDungeon.player;
        int maxHpReduction = (int)(p.maxHealth * MAX_HP_REDUCTION);
        p.decreaseMaxHealth(maxHpReduction);
    }

    @Override
    public void atBattleStart() {
        // 战斗开始时获得8层金属化
        this.flash();
        addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, METALLICIZE_AMOUNT), METALLICIZE_AMOUNT));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Coconut();
    }
}
