package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HypnoticWatch extends CustomRelic {
    public static final String ID = "echoForm:HypnoticWatch";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/HypnoticWatch.png";

    public HypnoticWatch() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        // 在前两个回合开始时，你可以自由选择敌人的意图
        // 这个效果需要修改敌人的意图显示逻辑
        // 暂时简化实现
    }

    @Override
    public void atTurnStart() {
        // 在前两个回合开始时，你可以自由选择敌人的意图
        // 这个效果需要修改敌人的意图显示逻辑
        // 暂时简化实现
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HypnoticWatch();
    }
}
