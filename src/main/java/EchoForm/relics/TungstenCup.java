package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class TungstenCup extends CustomRelic {
    public static final String ID = "echoForm:TungstenCup";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/TungstenCup.png";

    public TungstenCup() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.UNCOMMON, LandingSound.HEAVY);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public int onPlayerHeal(int healAmount) {
        // 玩家回复生命时，增加一点
        this.flash();
        // 这里需要实现一个机制来增加某种计数
        // 暂时简化实现，返回原始治疗量
        return healAmount;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        // 敌人失去生命时，增加一点
        this.flash();
        // 这里需要实现一个机制来增加某种计数
        // 暂时简化实现
    }

    @Override
    public AbstractRelic makeCopy() {
        return new TungstenCup();
    }
}