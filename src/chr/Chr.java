package chr;

import java.util.ArrayList;
import java.util.function.Supplier;

import action.Action;
import equipment.Equipment;
import item.Item;
import others.IO;

public abstract class Chr {
	private final int DEF_MULTI_DEFAULT = 1;
	public static final int MAX_HP = 9999;
	
	
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
	
	public int DEFMulti;
	
	public Party party;
	public ArrayList<Action> actions;
	public Action action;
	public ArrayList<Chr> targets;
	public String jobName;
	public ArrayList<Item> items;
	public Item item;
	public ArrayList<Equipment> equipments;
	public Equipment equipment;
	
	
	public final int MAX_ATK_COEF = 2;
	public final int MAX_DEF_COEF = 2;
	public final int MAX_MAT_COEF = 2;
	public final int MAX_MDF_COEF = 2;
	public final int MAX_SPD_COEF = 5;
	public final double MIN_ATK_COEF = 0.5;
	public final double MIN_DEF_COEF = 0.5;
	public final double MIN_MAT_COEF = 0.5;
	public final double MIN_MDF_COEF = 0.5;
	public final double MIN_SPD_COEF = 0.1;
	
	public Chr(String name, int maxHP, int maxMP, int baseATK, int baseDEF, int baseMAT, int baseMDF, int baseSPD) {
		this.name = name;
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
		
		DEFMulti = DEF_MULTI_DEFAULT;
		party = null;
		actions = new ArrayList<>();
		action = null;
		targets = new ArrayList<>();
		jobName = null;
		items = new ArrayList<>();
		item = null;
		equipments = new ArrayList<>();
		equipment = null;
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
