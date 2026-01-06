package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CurseOfCurse extends CustomRelic {
    public static final String ID = "echoForm:CurseOfCurse";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/CurseOfCurse.png";
    
    private static final int VULNERABLE_AMOUNT = 3;
    private static final int BLOCK_AMOUNT = 8;

    public CurseOfCurse() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 当你打出一张攻击牌时，给予目标3层易伤，获得8点格挡，将一张诅咒牌加入抽牌堆和弃牌堆
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            
            // 给予目标易伤
            if (m != null) {
                addToBot((AbstractGameAction)new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, VULNERABLE_AMOUNT, false), VULNERABLE_AMOUNT));
            }
            
            // 获得格挡
            addToBot((AbstractGameAction)new GainBlockAction(AbstractDungeon.player, BLOCK_AMOUNT));
            
            // 将一张诅咒牌加入抽牌堆和弃牌堆
            addToBot((AbstractGameAction)new MakeTempCardInDiscardAndDeckAction(new CurseOfTheBell()));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CurseOfCurse();
    }
}