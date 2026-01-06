package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CurseEye extends CustomRelic {
    public static final String ID = "echoForm:CurseEye";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/CurseEye.png";
    
    private static final int VULNERABLE_AMOUNT = 2;
    private static final int BLOCK_AMOUNT = 7;

    public CurseEye() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 当你打出一张攻击牌时，给予目标2层易伤，获得7点格挡，获得一张奇迹
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            
            // 给予目标易伤
            if (m != null) {
                addToBot((AbstractGameAction)new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, VULNERABLE_AMOUNT, false), VULNERABLE_AMOUNT));
            }
            
            // 获得格挡
            addToBot((AbstractGameAction)new GainBlockAction(AbstractDungeon.player, BLOCK_AMOUNT));
            
            // 获得一张奇迹
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(new Miracle(), 1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CurseEye();
    }
}