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
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ConfusingMushroom extends CustomRelic {
    public static final String ID = "echoForm:ConfusingMushroom";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/ConfusingMushroom.png";
    
    private static final int BLOCK_AMOUNT = 6;
    private static final int HEAL_AMOUNT = 3;

    public ConfusingMushroom() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 当你打出一张攻击牌时，给予所有敌人混乱，获得6点格挡，回复3点生命
        if (card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            
            // 给予所有敌人混乱
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!mo.isDead && !mo.isDying) {
                    addToBot((AbstractGameAction)new ApplyPowerAction(mo, AbstractDungeon.player, new ConfusionPower(mo)));
                }
            }
            
            // 获得格挡
            addToBot((AbstractGameAction)new GainBlockAction(AbstractDungeon.player, BLOCK_AMOUNT));
            
            // 回复生命
            addToBot((AbstractGameAction)new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ConfusingMushroom();
    }
}