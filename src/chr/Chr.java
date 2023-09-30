package chr;

import java.util.ArrayList;

import action.Action;
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
	
	
	public final int maxATKCoef = 2;
	public final int maxDEFCoef = 2;
	public final int maxMATCoef = 2;
	public final int maxMDFCoef = 2;
	public final int maxSPDCoef = 5;
	public final double minATKCoef = 0.5;
	public final double minDEFCoef = 0.5;
	public final double minMATCoef = 0.5;
	public final double minMDFCoef = 0.5;
	public final double minSPDCoef = 0.1;
	
	public Chr(String name, int maxHP, int maxMP, int baseATK, int baseDEF, int baseMAT, int baseMDF, int baseSPD) {
		this.name = name;
		this.HP = this.maxHP = maxHP;
		this.MP = this.maxMP = maxMP;
		this.ATK = this.baseATK = baseATK;
		this.DEF = this.baseDEF = baseDEF;
		this.MAT = this.baseMAT = baseMAT;
		this.MDF = this.baseMDF = baseMDF;
		this.SPD = this.baseSPD = baseSPD;
		this.maxATK = this.baseATK * maxATKCoef;
		this.maxDEF = this.baseDEF * maxDEFCoef;
		this.maxMAT = this.baseMAT * maxMATCoef;
		this.maxMDF = this.baseMDF * maxMDFCoef;
		this.maxSPD = this.baseSPD * maxSPDCoef;
		this.minATK = (int)(this.baseATK * minATKCoef);
		this.minDEF = (int)(this.baseDEF * minDEFCoef);
		this.minMAT = (int)(this.baseMAT * minMATCoef);
		this.minMDF = (int)(this.baseMDF * minMDFCoef);
		this.minSPD = (int)(this.baseSPD * minSPDCoef);
		
		DEFMulti = DEF_MULTI_DEFAULT;
		party = null;
		actions = new ArrayList<>();
		action = null;
		targets = new ArrayList<>();
		jobName = null;
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
	
	public Runnable command;
	
	public void setToPC() {
		command = this::playerCommand;
		for (Action a : actions) {
			a.target = a::playerTarget;
		}
	}
	
	public void playerCommand() {
		action = IO.printAndSelectPCAction(name, actions);
	}
	
	public abstract void nonPlayerCommand();
}
