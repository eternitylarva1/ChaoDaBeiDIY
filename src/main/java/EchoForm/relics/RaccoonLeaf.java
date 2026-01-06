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
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RaccoonLeaf extends CustomRelic {
    public static final String ID = "echoForm:RaccoonLeaf";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/RaccoonLeaf.png";
    
    private static final int INTANGIBLE_TURNS = 1;

    public RaccoonLeaf() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }

    @Override
    public void atBattleStart() {
        this.flash();
        // 角色无敌（出牌后失效）
        // 使用IntangiblePlayerPower来实现无敌效果
        addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, INTANGIBLE_TURNS), INTANGIBLE_TURNS));
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // 出牌后失效
        this.flash();
        // 移除无敌效果
        if (AbstractDungeon.player.hasPower(IntangiblePlayerPower.POWER_ID)) {
            addToBot((AbstractGameAction)new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IntangiblePlayerPower(AbstractDungeon.player, 0), 0));
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RaccoonLeaf();
    }
}