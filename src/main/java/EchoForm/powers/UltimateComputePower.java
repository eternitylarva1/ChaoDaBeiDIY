package EchoForm.powers;

import EchoForm.utils.TextureUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UltimateComputePower extends AbstractPower {
    public static final String POWER_ID = "UltimateComputePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private static final String IMG_PATH_128 = "echoFormResources/images/powers/UltimateComputePowerPower84.png";
    private static final String IMG_PATH_48 = "echoFormResources/images/powers/UltimateComputePowerPower32.png";

    private static TextureAtlas.AtlasRegion region128;
    private static TextureAtlas.AtlasRegion region48;

    static {
        Texture tex = ImageMaster.loadImage(IMG_PATH_128);
        if (tex != null) {
            region128 = new TextureAtlas.AtlasRegion(tex, 0, 0, 84, 84);
            region48 = TextureUtils.resizeTexture(region128, 48, 48);
        }
    }

    public UltimateComputePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img=ImageMaster.loadImage(IMG_PATH_48);
        this.region128 = region128;
        this.region48 = region48;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        this.flash();
        
        // 激发充能球时，其他充能球使用1次被动能力
        for (AbstractOrb otherOrb : AbstractDungeon.player.orbs) {
            if (otherOrb != orb && otherOrb != null) {
                otherOrb.onStartOfTurn() ;
            }
        }
    }
}