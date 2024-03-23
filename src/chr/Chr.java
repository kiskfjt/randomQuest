package chr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import action.Action;
import equipment.Equipment;
import item.Item;
import others.IO;

public abstract class Chr {
	public final int ATK_MULTI_DEFAULT = 1;
	public final int DEF_MULTI_DEFAULT = 1;
	public final int SPD_NEXT_DEFAULT = 0;
	public static final int MAX_HP = 9999;
	public final int MAX_SPD = 999;
	private final int LV_DEFAULT = 1;
	private final int ACTION_TURN_DEFAULT = 1;
	public static final int STATUS_TURN_DEFAULT = 0;
	private final double MAGIC_RESISTANCE_DEFAULT = 1.0;
	public final int EVASION_RATE_DEFAULT = 0;
	private final int EVASION_TURN_DEFAULT = 0;
	public final boolean COP_OUT_DEFAULT = false;
	private final boolean RESISTANCE_DEFAULT = true;
	
	// 状態異常の定数
	public static final int STATUS_NOMAL = 0;
	public static final int STATUS_POISONED = 1;
	public static final int STATUS_DEADLY_POISONED = 2;
	public static final int STATUS_PARALYZED = 3;
	public static final int STATUS_ASLEEP = 4;
	public static final int STATUS_CONFUSED = 5;
	public static final int STATUS_SILENT = 6;
	
	// 状態異常の名前
	public static final String STATUS_STR_POISONED = "どく";
	public static final String STATUS_STR_DEADLY_POISONED = "もうどく";
	public static final String STATUS_STR_PARALYZED = "まひ";
	public static final String STATUS_STR_ASLEEP = "眠り";
	public static final String STATUS_STR_CONFUSED = "混乱";
	public static final String STATUS_STR_SILENT = "沈黙";
	
	// スキルや魔法、アイテムによる状態変化の定数
	public static final int STATUS_INVINCIBLE = 20;
	public static final int STATUS_SING = 21;
	public static final int STATUS_MAGIC_BOUNCE = 22;
	public static final int STATUS_SKIP = 50;
	
	public static final int STATUS_DEAD = 99;
	public static final String STATUS_STR_DEAD = "しに";
	
	// 重複可能な状態変化のマップ
	public Map<Integer, Integer> statusMap;
	
	
	public String name;
	public int HP;
	public int MP;
	public int ATK;
	public int DEF;
	public int MAT;
	public int MDF;
	public int SPD;
	public int maxHP;
	public int maxMP;
	public int baseATK;
	public int baseDEF;
	public int baseMAT;
	public int baseMDF;
	public int baseSPD;
	public int maxATK;
	public int maxDEF;
	public int maxMAT;
	public int maxMDF;
	public int maxSPD;
	public int minATK;
	public int minDEF;
	public int minMAT;
	public int minMDF;
	public int minSPD;
	public int buffATK;
	public int buffDEF;
	public int buffMAT;
	public int buffMDF;
	public int buffSPD;
	
	public int Lv;
	public double ATKNext;
	public double DEFNext;
	public int actionTurn;
	public int actionTurnDefault;
	public int SPDNext;
	public int status;
	public int statusTurn;
	public String statusStr;
	public double magicResistance;
	// 回避率
	public int evasionRate;
	public int evasionTurn;
	// 受け流し
	public boolean copOut;
	// キャラ属性
	public int element;
	public String elementStr;
	
	// 属性番号
	public static final int CHR_ELEMENT_NOMAL = 0;
	public static final int CHR_ELEMENT_FIRE = 1;
	public static final int CHR_ELEMENT_WATER = 2;
	public static final int CHR_ELEMENT_THUNDER = 3;
	public static final int CHR_ELEMENT_EARTH = 4;
	public static final int CHR_ELEMENT_AIR = 5;
	public static final int CHR_ELEMENT_LIGHT = 6;
	public static final int CHR_ELEMENT_DARK = 7;
	// 属性の名前
	public static final String STR_ELEMENT_NOMAL = "無";
	public static final String STR_ELEMENT_FIRE = "火";
	public static final String STR_ELEMENT_WATER = "水";
	public static final String STR_ELEMENT_THUNDER = "雷";
	public static final String STR_ELEMENT_EARTH = "土";
	public static final String STR_ELEMENT_AIR = "風";
	public static final String STR_ELEMENT_LIGHT = "光";
	public static final String STR_ELEMENT_DARK = "闇";
	// 属性番号と名前のマップ
	public static Map<Integer, String> elementMap = makeElementMap();
	
	// 耐性関連
	public boolean canLowerATK;
	public boolean canLowerDEF;
	public boolean canLowerMAT;
	public boolean canLowerMDF;
	public boolean canLowerSPD;
	public boolean canLowerMagicResistance;
	public boolean canBeKilledInstantly;
	
	// 状態異常耐性
	public boolean canBePoisoned;
	public boolean canBeParalyzed;
	public boolean canBeAsleep;
	public boolean canBeConfused;
	public boolean canBeSilent;
	
	// アクション禁止タイプのリスト
	public ArrayList<Integer> bannedActionTypeList;
	
	public Party party;
	public ArrayList<Action> actions;
	public Action action;
	public ArrayList<Chr> targets;
	public String jobName;
	public ArrayList<Item> items;
	public Item item;
	public ArrayList<Equipment> equipments;
	public Equipment equipment;
	
	// public boolean attackedFlg;
	
	
	public final int MAX_ATK_COEF = 2;
	public final int MAX_DEF_COEF = 2;
	public final int MAX_MAT_COEF = 2;
	public final int MAX_MDF_COEF = 2;
	public final int MAX_SPD_COEF = 5;
	public final double MIN_ATK_COEF = 0.5;
	public final double MIN_DEF_COEF = 0.5;
	public final double MIN_MAT_COEF = 0.5;
	public final double MIN_MDF_COEF = 0.5;
	public final double MIN_SPD_COEF = 0.5;
	
	public Chr(int maxHP, int maxMP, int baseATK, int baseDEF, int baseMAT, int baseMDF, int baseSPD) {
		this.HP = this.maxHP = maxHP;
		this.MP = this.maxMP = maxMP;
		this.ATK = this.baseATK = baseATK;
		this.DEF = this.baseDEF = baseDEF;
		this.MAT = this.baseMAT = baseMAT;
		this.MDF = this.baseMDF = baseMDF;
		this.SPD = this.baseSPD = baseSPD;
		this.maxATK = this.baseATK * MAX_ATK_COEF;
		this.maxDEF = this.baseDEF * MAX_DEF_COEF;
		this.maxMAT = this.baseMAT * MAX_MAT_COEF;
		this.maxMDF = this.baseMDF * MAX_MDF_COEF;
		this.maxSPD = this.baseSPD * MAX_SPD_COEF;
		this.minATK = (int)(this.baseATK * MIN_ATK_COEF);
		this.minDEF = (int)(this.baseDEF * MIN_DEF_COEF);
		this.minMAT = (int)(this.baseMAT * MIN_MAT_COEF);
		this.minMDF = (int)(this.baseMDF * MIN_MDF_COEF);
		this.minSPD = (int)(this.baseSPD * MIN_SPD_COEF);
		
		ATKNext = ATK_MULTI_DEFAULT;
		DEFNext = DEF_MULTI_DEFAULT;
		party = null;
		actions = new ArrayList<>();
		action = null;
		targets = new ArrayList<>();
		jobName = null;
		items = new ArrayList<>();
		item = null;
		equipments = new ArrayList<>();
		equipment = null;
		Lv = LV_DEFAULT;
		actionTurnDefault = ACTION_TURN_DEFAULT;
		// attackedFlg = ATTACKED_FLG_DEFAULT;
		SPDNext = SPD_NEXT_DEFAULT;
		status = STATUS_NOMAL;
		statusTurn = STATUS_TURN_DEFAULT;
		statusStr = "";
		magicResistance = MAGIC_RESISTANCE_DEFAULT;
		evasionRate = EVASION_RATE_DEFAULT;
		evasionTurn = EVASION_TURN_DEFAULT;
		copOut = COP_OUT_DEFAULT;
		element = CHR_ELEMENT_NOMAL;
		elementStr = STR_ELEMENT_NOMAL;
		// 耐性関連
		canLowerATK = RESISTANCE_DEFAULT;
		canLowerDEF = RESISTANCE_DEFAULT;
		canLowerMAT = RESISTANCE_DEFAULT;
		canLowerMDF = RESISTANCE_DEFAULT;
		canLowerSPD = RESISTANCE_DEFAULT;
		canLowerMagicResistance = RESISTANCE_DEFAULT;
		
		// 状態異常耐性関連
		canBePoisoned = RESISTANCE_DEFAULT;
		canBeParalyzed = RESISTANCE_DEFAULT;
		canBeAsleep = RESISTANCE_DEFAULT;
		canBeConfused = RESISTANCE_DEFAULT;
		canBeSilent = RESISTANCE_DEFAULT;
		
		statusMap = new HashMap<>();
		bannedActionTypeList = new ArrayList<>();
	}
	
	public Chr() {
	}
	
	public int getSPD() {
		return SPD;
	}
	
	public boolean isAlive() {
		return HP > 0;
	}
	
	public boolean isDead() {
		return HP <= 0;
	}
	
	private static Map<Integer, String> makeElementMap() {
		Map<Integer, String> elementMap = new HashMap<>();
		
		elementMap.put(CHR_ELEMENT_NOMAL, STR_ELEMENT_NOMAL);
		elementMap.put(CHR_ELEMENT_FIRE, STR_ELEMENT_FIRE);
		elementMap.put(CHR_ELEMENT_WATER, STR_ELEMENT_WATER);
		elementMap.put(CHR_ELEMENT_THUNDER, STR_ELEMENT_THUNDER);
		elementMap.put(CHR_ELEMENT_EARTH, STR_ELEMENT_EARTH);
		elementMap.put(CHR_ELEMENT_AIR, STR_ELEMENT_AIR);
		elementMap.put(CHR_ELEMENT_LIGHT, STR_ELEMENT_LIGHT);
		elementMap.put(CHR_ELEMENT_DARK, STR_ELEMENT_DARK);
		
		return elementMap;
	}
	
	// public Runnable command;
	public Supplier<Boolean> command;
	
	public void setToPC() {
		command = this::playerCommand;
		for (Action a : actions) {
			a.target = a::playerTarget;
		}
	}
	
	public boolean playerCommand() {
		return IO.selectPCAction(this);
	}
	
	public abstract boolean nonPlayerCommand();
}
