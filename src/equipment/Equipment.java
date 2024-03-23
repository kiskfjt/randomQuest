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
	
	private final int EQUIPMENT_STATUS_DEFAULT = 0;
	public int HP = EQUIPMENT_STATUS_DEFAULT;
	public int MP = EQUIPMENT_STATUS_DEFAULT;
	public int ATK = EQUIPMENT_STATUS_DEFAULT;
	public int DEF = EQUIPMENT_STATUS_DEFAULT;
	public int MAT = EQUIPMENT_STATUS_DEFAULT;
	public int MDF = EQUIPMENT_STATUS_DEFAULT;
	public int SPD = EQUIPMENT_STATUS_DEFAULT;
	
	// 属性ポイント（キャラの属性決定時に使う）
	private final int ELEMENT_POINT_DEFAULT = 0;
	public int elementPointFire = ELEMENT_POINT_DEFAULT;
	public int elementPointWater = ELEMENT_POINT_DEFAULT;
	public int elementPointThunder = ELEMENT_POINT_DEFAULT;
	public int elementPointEarth = ELEMENT_POINT_DEFAULT;
	public int elementPointAir = ELEMENT_POINT_DEFAULT;
	public int elementPointLight = ELEMENT_POINT_DEFAULT;
	public int elementPointDark = ELEMENT_POINT_DEFAULT;
	
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
