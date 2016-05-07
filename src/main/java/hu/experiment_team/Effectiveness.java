package hu.experiment_team;

import java.util.HashMap;
import java.util.Map;

/**
 * Ebből az osztályból lehet lekérni az adott típushoz tartozó bónusz sebzést.
 * @author Jakab Ádám
 * */
public enum Effectiveness {

    /**
     * Instance of the singleton class
     * */
    INSTANCE;

    /**
     * A típusok bónusz sebzésének listája.
     * A kulcs a két típus nevének összekulcsolása, az érték pedig a sebzésbónusz.
     * */
    private static final Map<String, Double> typeEffectiveness;
    static {
        typeEffectiveness = new HashMap<>();
        // NORMAL
        typeEffectiveness.put("NORMALNORMAL", 1.0); typeEffectiveness.put("NORMALFIRE", 1.0); typeEffectiveness.put("NORMALWATER", 1.0);typeEffectiveness.put("NORMALELECTRIC", 1.0);
        typeEffectiveness.put("NORMALGRASS", 1.0);typeEffectiveness.put("NORMALICE", 1.0);typeEffectiveness.put("NORMALFIGHTING", 1.0);typeEffectiveness.put("NORMALPOISON", 1.0);
        typeEffectiveness.put("NORMALGROUND", 1.0);typeEffectiveness.put("NORMALFLYING", 1.0);typeEffectiveness.put("NORMALPSYCHIC", 1.0);typeEffectiveness.put("NORMALBUG", 1.0);
        typeEffectiveness.put("NORMALROCK", 0.5);typeEffectiveness.put("NORMALGHOST", 0.0);typeEffectiveness.put("NORMALDRAGON", 1.0);typeEffectiveness.put("NORMALDARK", 1.0);
        typeEffectiveness.put("NORMALSTEEL", 0.5);typeEffectiveness.put("NORMALFAIRY", 1.0);
        // FIRE
        typeEffectiveness.put("FIRENORMAL", 1.0);typeEffectiveness.put("FIREFIRE", 0.5);typeEffectiveness.put("FIREWATER", 0.5);typeEffectiveness.put("FIREELECTRIC", 1.0);
        typeEffectiveness.put("FIREGRASS", 2.0);typeEffectiveness.put("FIREICE", 2.0);typeEffectiveness.put("FIREFIGHTING", 1.0);typeEffectiveness.put("FIREPOISON", 1.0);
        typeEffectiveness.put("FIREGROUND", 1.0);typeEffectiveness.put("FIREFLYING", 1.0);typeEffectiveness.put("FIREPSYCHIC", 1.0);typeEffectiveness.put("FIREBUG", 2.0);
        typeEffectiveness.put("FIREROCK", 0.5);typeEffectiveness.put("FIREGHOST", 1.0);typeEffectiveness.put("FIREDRAGON", 0.5);typeEffectiveness.put("FIREDARK", 1.0);
        typeEffectiveness.put("FIRESTEEL", 2.0);typeEffectiveness.put("FIREFAIRY", 1.0);
        // WATER
        typeEffectiveness.put("WATERNORMAL", 1.0);typeEffectiveness.put("WATERFIRE", 2.0);typeEffectiveness.put("WATERWATER", 0.5);typeEffectiveness.put("WATERELECTRIC", 1.0);
        typeEffectiveness.put("WATERGRASS", 0.5);typeEffectiveness.put("WATERICE", 1.0);typeEffectiveness.put("WATERFIGHTING", 1.0);typeEffectiveness.put("WATERPOISON", 1.0);
        typeEffectiveness.put("WATERGROUND", 2.0);typeEffectiveness.put("WATERFLYING", 1.0);typeEffectiveness.put("WATERPSYCHIC", 1.0);typeEffectiveness.put("WATERBUG", 1.0);
        typeEffectiveness.put("WATERROCK", 2.0);typeEffectiveness.put("WATERGHOST", 1.0);typeEffectiveness.put("WATERDRAGON", 0.5);typeEffectiveness.put("WATERDARK", 1.0);
        typeEffectiveness.put("WATERSTEEL", 1.0);typeEffectiveness.put("WATERFAIRY", 1.0);
        // ELECTRIC
        typeEffectiveness.put("ELECTRICNORMAL", 1.0);typeEffectiveness.put("ELECTRICFIRE", 1.0);typeEffectiveness.put("ELECTRICWATER", 2.0);typeEffectiveness.put("ELECTRICELECTRIC", 0.5);
        typeEffectiveness.put("ELECTRICGRASS", 0.5);typeEffectiveness.put("ELECTRICICE", 1.0);typeEffectiveness.put("ELECTRICFIGHTING", 1.0);typeEffectiveness.put("ELECTRICPOISON", 1.0);
        typeEffectiveness.put("ELECTRICGROUND", 0.0);typeEffectiveness.put("ELECTRICFLYING", 2.0);typeEffectiveness.put("ELECTRICPSYCHIC", 1.0);typeEffectiveness.put("ELECTRICBUG", 1.0);
        typeEffectiveness.put("ELECTRICROCK", 1.0);typeEffectiveness.put("ELECTRICGHOST", 1.0);typeEffectiveness.put("ELECTRICDRAGON", 0.5);typeEffectiveness.put("ELECTRICDARK", 1.0);
        typeEffectiveness.put("ELECTRICSTEEL", 1.0);typeEffectiveness.put("ELECTRICFAIRY", 1.0);
        // GRASS
        typeEffectiveness.put("GRASSNORMAL", 1.0);typeEffectiveness.put("GRASSFIRE", 0.5);typeEffectiveness.put("GRASSWATER", 2.0);typeEffectiveness.put("GRASSELECTRIC", 1.0);
        typeEffectiveness.put("GRASSGRASS", 0.5);typeEffectiveness.put("GRASSICE", 1.0);typeEffectiveness.put("GRASSFIGHTING", 1.0);typeEffectiveness.put("GRASSPOISON", 0.5);
        typeEffectiveness.put("GRASSGROUND", 2.0);typeEffectiveness.put("GRASSFLYING", 0.5);typeEffectiveness.put("GRASSPSYCHIC", 1.0);typeEffectiveness.put("GRASSBUG", 0.5);
        typeEffectiveness.put("GRASSROCK", 2.0);typeEffectiveness.put("GRASSGHOST", 1.0);typeEffectiveness.put("GRASSDRAGON", 0.5);typeEffectiveness.put("GRASSDARK", 1.0);
        typeEffectiveness.put("GRASSSTEEL", 0.5);typeEffectiveness.put("GRASSFAIRY", 1.0);
        // ICE
        typeEffectiveness.put("ICENORMAL", 1.0);typeEffectiveness.put("ICEFIRE", 0.5);typeEffectiveness.put("ICEWATER", 0.5);typeEffectiveness.put("ICEELECTRIC", 1.0);
        typeEffectiveness.put("ICEGRASS", 2.0);typeEffectiveness.put("ICEICE", 0.5);typeEffectiveness.put("ICEFIGHTING", 1.0);typeEffectiveness.put("ICEPOISON", 1.0);
        typeEffectiveness.put("ICEGROUND", 2.0);typeEffectiveness.put("ICEFLYING", 2.0);typeEffectiveness.put("ICEPSYCHIC", 1.0);typeEffectiveness.put("ICEBUG", 1.0);
        typeEffectiveness.put("ICEROCK", 1.0);typeEffectiveness.put("ICEGHOST", 1.0);typeEffectiveness.put("ICEDRAGON", 2.0);typeEffectiveness.put("ICEDARK", 1.0);
        typeEffectiveness.put("ICESTEEL", 0.5);typeEffectiveness.put("ICEFAIRY", 1.0);
        // ING
        typeEffectiveness.put("INGNORMAL", 2.0);typeEffectiveness.put("INGFIRE", 1.0);typeEffectiveness.put("INGWATER", 1.0);typeEffectiveness.put("INGELECTRIC", 1.0);
        typeEffectiveness.put("INGGRASS", 1.0);typeEffectiveness.put("INGICE", 2.0);typeEffectiveness.put("INGING", 1.0);typeEffectiveness.put("INGPOISON", 0.5);
        typeEffectiveness.put("INGGROUND", 1.0);typeEffectiveness.put("INGFLYING", 0.5);typeEffectiveness.put("INGPSYCHIC", 0.5);typeEffectiveness.put("INGBUG", 0.5);
        typeEffectiveness.put("INGROCK", 2.0);typeEffectiveness.put("INGGHOST", 0.0);typeEffectiveness.put("INGDRAGON", 1.0);typeEffectiveness.put("INGDARK", 2.0);
        typeEffectiveness.put("INGSTEEL", 2.0);typeEffectiveness.put("INGFAIRY", 0.5);
        // POISON
        typeEffectiveness.put("POISONNORMAL", 1.0);typeEffectiveness.put("POISONFIRE", 1.0);typeEffectiveness.put("POISONWATER", 1.0);typeEffectiveness.put("POISONELECTRIC", 1.0);
        typeEffectiveness.put("POISONGRASS", 2.0);typeEffectiveness.put("POISONICE", 1.0);typeEffectiveness.put("POISONING", 1.0);typeEffectiveness.put("POISONPOISON", 0.5);
        typeEffectiveness.put("POISONGROUND", 0.5);typeEffectiveness.put("POISONFLYING", 1.0);typeEffectiveness.put("POISONPSYCHIC", 1.0);typeEffectiveness.put("POISONBUG", 1.0);
        typeEffectiveness.put("POISONROCK", 0.5);typeEffectiveness.put("POISONGHOST", 0.5);typeEffectiveness.put("POISONDRAGON", 1.0);typeEffectiveness.put("POISONDARK", 1.0);
        typeEffectiveness.put("POISONSTEEL", 0.0);typeEffectiveness.put("POISONFAIRY", 2.0);
        // GROUND
        typeEffectiveness.put("GROUNDNORMAL", 1.0);typeEffectiveness.put("GROUNDFIRE", 2.0);typeEffectiveness.put("GROUNDWATER", 1.0);typeEffectiveness.put("GROUNDELECTRIC", 2.0);
        typeEffectiveness.put("GROUNDGRASS", 0.5);typeEffectiveness.put("GROUNDICE", 1.0);typeEffectiveness.put("GROUNDING", 1.0);typeEffectiveness.put("GROUNDPOISON", 2.0);
        typeEffectiveness.put("GROUNDGROUND", 1.0);typeEffectiveness.put("GROUNDFLYING", 0.0);typeEffectiveness.put("GROUNDPSYCHIC", 1.0);typeEffectiveness.put("GROUNDBUG", 0.5);
        typeEffectiveness.put("GROUNDROCK", 2.0);typeEffectiveness.put("GROUNDGHOST", 1.0);typeEffectiveness.put("GROUNDDRAGON", 1.0);typeEffectiveness.put("GROUNDDARK", 1.0);
        typeEffectiveness.put("GROUNDSTEEL", 2.0);typeEffectiveness.put("GROUNDFAIRY", 1.0);
        // FLYING
        typeEffectiveness.put("FLYINGNORMAL", 1.0);typeEffectiveness.put("FLYINGFIRE", 1.0);typeEffectiveness.put("FLYINGWATER", 1.0);typeEffectiveness.put("FLYINGELECTRIC", 0.5);
        typeEffectiveness.put("FLYINGGRASS", 2.0);typeEffectiveness.put("FLYINGICE", 1.0);typeEffectiveness.put("FLYINGING", 2.0);typeEffectiveness.put("FLYINGPOISON", 2.0);
        typeEffectiveness.put("FLYINGGROUND", 1.0);typeEffectiveness.put("FLYINGFLYING", 1.0);typeEffectiveness.put("FLYINGPSYCHIC", 1.0);typeEffectiveness.put("FLYINGBUG", 2.0);
        typeEffectiveness.put("FLYINGROCK", 0.5);typeEffectiveness.put("FLYINGGHOST", 1.0);typeEffectiveness.put("FLYINGDRAGON", 1.0);typeEffectiveness.put("FLYINGDARK", 1.0);
        typeEffectiveness.put("FLYINGSTEEL", 0.5);typeEffectiveness.put("FLYINGFAIRY", 1.0);
        // PSYCHIC
        typeEffectiveness.put("PSYCHICNORMAL", 1.0);typeEffectiveness.put("PSYCHICFIRE", 1.0);typeEffectiveness.put("PSYCHICWATER", 1.0);typeEffectiveness.put("PSYCHICELECTRIC", 1.0);
        typeEffectiveness.put("PSYCHICGRASS", 1.0);typeEffectiveness.put("PSYCHICICE", 1.0);typeEffectiveness.put("PSYCHICING", 2.0);typeEffectiveness.put("PSYCHICPOISON", 2.0);
        typeEffectiveness.put("PSYCHICGROUND", 1.0);typeEffectiveness.put("PSYCHICFLYING", 1.0);typeEffectiveness.put("PSYCHICPSYCHIC", 0.5);typeEffectiveness.put("PSYCHICBUG", 1.0);
        typeEffectiveness.put("PSYCHICROCK", 1.0);typeEffectiveness.put("PSYCHICGHOST", 1.0);typeEffectiveness.put("PSYCHICDRAGON", 1.0);typeEffectiveness.put("PSYCHICDARK", 0.0);
        typeEffectiveness.put("PSYCHICSTEEL", 0.5);typeEffectiveness.put("PSYCHICFAIRY", 1.0);
        // BUG
        typeEffectiveness.put("BUGNORMAL", 1.0);typeEffectiveness.put("BUGFIRE", 0.5);typeEffectiveness.put("BUGWATER", 1.0);typeEffectiveness.put("BUGELECTRIC", 1.0);
        typeEffectiveness.put("BUGGRASS", 2.0);typeEffectiveness.put("BUGICE", 1.0);typeEffectiveness.put("BUGING", 0.5);typeEffectiveness.put("BUGPOISON", 0.5);
        typeEffectiveness.put("BUGGROUND", 1.0);typeEffectiveness.put("BUGFLYING", 0.5);typeEffectiveness.put("BUGPSYCHIC", 2.0);typeEffectiveness.put("BUGBUG", 1.0);
        typeEffectiveness.put("BUGROCK", 1.0);typeEffectiveness.put("BUGGHOST", 0.5);typeEffectiveness.put("BUGDRAGON", 1.0);typeEffectiveness.put("BUGDARK", 2.0);
        typeEffectiveness.put("BUGSTEEL", 0.5);typeEffectiveness.put("BUGFAIRY", 0.5);
        // ROCK
        typeEffectiveness.put("ROCKNORMAL", 1.0);typeEffectiveness.put("ROCKFIRE", 2.0);typeEffectiveness.put("ROCKWATER", 1.0);typeEffectiveness.put("ROCKELECTRIC", 1.0);
        typeEffectiveness.put("ROCKGRASS", 1.0);typeEffectiveness.put("ROCKICE", 2.0);typeEffectiveness.put("ROCKFIGHTING", 0.5);typeEffectiveness.put("ROCKPOISON", 1.0);
        typeEffectiveness.put("ROCKGROUND", 0.5);typeEffectiveness.put("ROCKFLYING", 2.0);typeEffectiveness.put("ROCKPSYCHIC", 1.0);typeEffectiveness.put("ROCKBUG", 2.0);
        typeEffectiveness.put("ROCKROCK", 1.0);typeEffectiveness.put("ROCKGHOST", 1.0);typeEffectiveness.put("ROCKDRAGON", 1.0);typeEffectiveness.put("ROCKDARK", 1.0);
        typeEffectiveness.put("ROCKSTEEL", 0.5);typeEffectiveness.put("ROCKFAIRY", 1.0);
        // GHOST
        typeEffectiveness.put("GHOSTNORMAL", 0.0);typeEffectiveness.put("GHOSTFIRE", 1.0);typeEffectiveness.put("GHOSTWATER", 1.0);typeEffectiveness.put("GHOSTELECTRIC", 1.0);
        typeEffectiveness.put("GHOSTGRASS", 1.0);typeEffectiveness.put("GHOSTICE", 1.0);typeEffectiveness.put("GHOSTING", 1.0);typeEffectiveness.put("GHOSTPOISON", 1.0);
        typeEffectiveness.put("GHOSTGROUND", 1.0);typeEffectiveness.put("GHOSTFLYING", 1.0);typeEffectiveness.put("GHOSTPSYCHIC", 2.0);typeEffectiveness.put("GHOSTBUG", 1.0);
        typeEffectiveness.put("GHOSTROCK", 1.0);typeEffectiveness.put("GHOSTGHOST", 2.0);typeEffectiveness.put("GHOSTDRAGON", 1.0);typeEffectiveness.put("GHOSTDARK", 0.5);
        typeEffectiveness.put("GHOSTSTEEL", 1.0);typeEffectiveness.put("GHOSTFAIRY", 1.0);
        // DRAGON
        typeEffectiveness.put("DRAGONNORMAL", 1.0);typeEffectiveness.put("DRAGONFIRE", 1.0);typeEffectiveness.put("DRAGONWATER", 1.0);typeEffectiveness.put("DRAGONELECTRIC", 1.0);
        typeEffectiveness.put("DRAGONGRASS", 1.0);typeEffectiveness.put("DRAGONICE", 1.0);typeEffectiveness.put("DRAGONING", 1.0);typeEffectiveness.put("DRAGONPOISON", 1.0);
        typeEffectiveness.put("DRAGONGROUND", 1.0);typeEffectiveness.put("DRAGONFLYING", 1.0);typeEffectiveness.put("DRAGONPSYCHIC", 1.0);typeEffectiveness.put("DRAGONBUG", 1.0);
        typeEffectiveness.put("DRAGONROCK", 1.0);typeEffectiveness.put("DRAGONGHOST", 1.0);typeEffectiveness.put("DRAGONDRAGON", 2.0);typeEffectiveness.put("DRAGONDARK", 1.0);
        typeEffectiveness.put("DRAGONSTEEL", 0.5);typeEffectiveness.put("DRAGONFAIRY", 0.0);
        // DARK
        typeEffectiveness.put("DARKNORMAL", 1.0);typeEffectiveness.put("DARKFIRE", 1.0);typeEffectiveness.put("DARKWATER", 1.0);typeEffectiveness.put("DARKELECTRIC", 1.0);
        typeEffectiveness.put("DARKGRASS", 1.0);typeEffectiveness.put("DARKICE", 1.0);typeEffectiveness.put("DARKING", 0.5);typeEffectiveness.put("DARKPOISON", 1.0);
        typeEffectiveness.put("DARKGROUND", 1.0);typeEffectiveness.put("DARKFLYING", 1.0);typeEffectiveness.put("DARKPSYCHIC", 2.0);typeEffectiveness.put("DARKBUG", 1.0);
        typeEffectiveness.put("DARKROCK", 1.0);typeEffectiveness.put("DARKGHOST", 2.0);typeEffectiveness.put("DARKDRAGON", 1.0);typeEffectiveness.put("DARKDARK", 0.5);
        typeEffectiveness.put("DARKSTEEL", 1.0);typeEffectiveness.put("DARKFAIRY", 0.5);
        // STEEL
        typeEffectiveness.put("STEELNORMAL", 1.0);typeEffectiveness.put("STEELFIRE", 0.5);typeEffectiveness.put("STEELWATER", 0.5);typeEffectiveness.put("STEELELECTRIC", 0.5);
        typeEffectiveness.put("STEELGRASS", 1.0);typeEffectiveness.put("STEELICE", 2.0);typeEffectiveness.put("STEELING", 1.0);typeEffectiveness.put("STEELPOISON", 1.0);
        typeEffectiveness.put("STEELGROUND", 1.0);typeEffectiveness.put("STEELFLYING", 1.0);typeEffectiveness.put("STEELPSYCHIC", 1.0);typeEffectiveness.put("STEELBUG", 1.0);
        typeEffectiveness.put("STEELROCK", 2.0);typeEffectiveness.put("STEELGHOST", 1.0);typeEffectiveness.put("STEELDRAGON", 1.0);typeEffectiveness.put("STEELDARK", 1.0);
        typeEffectiveness.put("STEELSTEEL", 0.5);typeEffectiveness.put("STEELFAIRY", 2.0);
        // FAIRY
        typeEffectiveness.put("FAIRYNORMAL", 1.0);typeEffectiveness.put("FAIRYFIRE", 0.5);typeEffectiveness.put("FAIRYWATER", 1.0);typeEffectiveness.put("FAIRYELECTRIC", 1.0);
        typeEffectiveness.put("FAIRYGRASS", 1.0);typeEffectiveness.put("FAIRYICE", 1.0);typeEffectiveness.put("FAIRYING", 2.0);typeEffectiveness.put("FAIRYPOISON", 0.5);
        typeEffectiveness.put("FAIRYGROUND", 1.0);typeEffectiveness.put("FAIRYFLYING", 1.0);typeEffectiveness.put("FAIRYPSYCHIC", 1.0);typeEffectiveness.put("FAIRYBUG", 1.0);
        typeEffectiveness.put("FAIRYROCK", 1.0);typeEffectiveness.put("FAIRYGHOST", 1.0);typeEffectiveness.put("FAIRYDRAGON", 2.0);typeEffectiveness.put("FAIRYDARK", 2.0);
        typeEffectiveness.put("FAIRYSTEEL", 0.5);typeEffectiveness.put("FAIRYFAIRY", 1.0);
    }

    /**
     * @param type1 Type of the used move
     * @param type2 Type of the DEFENDER pokemon
     * @return This method returns the effectiveness of the used attack
     * */
    public double get(String type1, String type2){
        return typeEffectiveness.get(type1+type2);
    }

}
