package EchoForm.powers;

import EchoForm.utils.TextureUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.ArrayList;

public class NeurotoxinPower extends AbstractPower {
    public static final String POWER_ID = "NeurotoxinPower";
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    
    private static final String IMG_PATH_128 = "echoFormResources/images/powers/NeurotoxinPowerPower84.png";
    private static final String IMG_PATH_48 = "echoFormResources/images/powers/NeurotoxinPowerPower32.png";

    private static TextureAtlas.AtlasRegion region128;
    private static TextureAtlas.AtlasRegion region48;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        
        Texture tex = ImageMaster.loadImage(IMG_PATH_128);
        if (tex != null) {
            region128 = new TextureAtlas.AtlasRegion(tex, 0, 0, 84, 84);
            region48 = TextureUtils.resizeTexture(region128, 48, 48);
        }
    }

    public NeurotoxinPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img=ImageMaster.loadImage(IMG_PATH_48);
        this.region128 = region128;
        this.region48 = region48;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
    
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        // 在怪物回合结束时，重置怪物的攻击意图
        if (!isPlayer) {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (m.hasPower(POWER_ID)) {
                    m.createIntent();
                }
            }
        }
    }
}
