package action;

import java.util.function.Supplier;

import chr.Chr;

public abstract class Action {
	// public Runnable target;
	public Supplier<Boolean> target;
	public String name;
	public abstract boolean playerTarget();
	public abstract void execute();
	public Chr me;
	
	// 攻撃の倍率
	public int multi;
	public static final int MULTI_DEFAULT_VALUE = 100;
	
	// 攻撃、回復等の範囲の最低値、最大値
	public int rangeMin;
	public int rangeMax;
	public static final int RANGE_MIN_DEFAULT_VALUE = 80;
	public static final int RANGE_MAX_DEFAULT_VALUE = 120;
	public static final int RANGE_DEFAULT_VALUE = 100;
	
	
	public Action(Chr me) {
		this.me = me;
		this.target = this::playerTarget;
	}
}
