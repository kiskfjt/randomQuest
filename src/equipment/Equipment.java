package equipment;

import action.Action;
import chr.Chr;
import item.Item;

public abstract class Equipment extends Item {
	
	public Equipment(Chr me) {
		super(me);
		multi = Action.MULTI_DEFAULT_VALUE;
		rangeMin = Action.RANGE_MIN_DEFAULT_VALUE;
		rangeMax = Action.RANGE_MAX_DEFAULT_VALUE;
		addStatus();
	}
	
	public int HP = 0;
	public int MP = 0;
	public int ATK = 0;
	public int DEF = 0;
	public int MAT = 0;
	public int MDF = 0;
	public int SPD = 0;
	
	public void addStatus() {
		me.HP = me.maxHP += HP;
		me.MP = me.maxMP += MP;
		me.ATK = me.baseATK += ATK;
		me.DEF = me.baseDEF += DEF;
		me.MAT = me.baseMAT += MAT;
		me.MDF = me.baseMDF += MDF;
		me.SPD = me.baseSPD += SPD;
	}
}
