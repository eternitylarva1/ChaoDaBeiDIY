package EchoForm.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class OliveBranch extends CustomRelic {
    public static final String ID = "echoForm:OliveBranch";
    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final String IMG = "echoFormResources/images/relics/OliveBranch.png";
    
    private int extraTurnsThisBattle = 0;
    private static final int MAX_EXTRA_TURNS = 2;

    public OliveBranch() {
        super(ID, new Texture(Gdx.files.internal(IMG)), RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        this.extraTurnsThisBattle = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        // 回合结束时，如果场上的怪物都是满血，额外获得一回合
        if (this.extraTurnsThisBattle < MAX_EXTRA_TURNS) {
            boolean allMonstersFullHealth = true;
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.currentHealth < m.maxHealth) {
                    allMonstersFullHealth = false;
                    break;
                }
            }
            
            if (allMonstersFullHealth && !AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                this.flash();
                this.extraTurnsThisBattle++;
                // 额外获得一回合：抽5张牌，获得3点能量
                addToBot((AbstractGameAction)new DrawCardAction(AbstractDungeon.player, 5));
                addToBot((AbstractGameAction)new GainEnergyAction(3));
            }
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OliveBranch();
    }
}