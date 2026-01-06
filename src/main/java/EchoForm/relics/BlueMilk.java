package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class BlueMilk extends CustomRelic {
    public static final String ID = "echoForm:BlueMilk";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/BlueMilk.png";
    
    private static final int ARTIFACT_AMOUNT = 1;
    private static final int BLOCK_AMOUNT = 8;
    private static final int HEAL_AMOUNT = 4;

    public BlueMilk() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 当你打出一张攻击牌时，获得1层人工制品，获得8点格挡，回复4点生命
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            
            // 获得人工制品
            addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(AbstractDungeon.player, ARTIFACT_AMOUNT), ARTIFACT_AMOUNT));
            
            // 获得格挡
            addToBot((AbstractGameAction)new GainBlockAction(AbstractDungeon.player, BLOCK_AMOUNT));
            
            // 回复生命
            addToBot((AbstractGameAction)new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BlueMilk();
    }
}