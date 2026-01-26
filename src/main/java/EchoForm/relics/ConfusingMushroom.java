package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class ConfusingMushroom extends CustomRelic {
    public static final String ID = "echoForm:ConfusingMushroom";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/ConfusingMushroom.png";
    
    private static final int MAX_HP_BONUS = 10;

    public ConfusingMushroom() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        // 拾取时获得10点血量上限
        AbstractDungeon.player.increaseMaxHp(MAX_HP_BONUS, true);
    }

    @Override
    public void onPlayCard(AbstractCard card, com.megacrit.cardcrawl.monsters.AbstractMonster m) {
        // 每打出一张牌，丢弃最左侧的卡牌，然后抽一张牌
        if (!AbstractDungeon.player.hand.isEmpty()) {
            this.flash();
            // 丢弃最左侧的卡牌
            AbstractCard leftmostCard = AbstractDungeon.player.hand.group.get(0);
            addToBot((AbstractGameAction)new ExhaustSpecificCardAction(leftmostCard, AbstractDungeon.player.hand));
            // 抽一张牌
            addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 1));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new ConfusingMushroom();
    }
}