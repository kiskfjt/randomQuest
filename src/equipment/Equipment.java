package equipment;

import action.Action;
import chr.Chr;

public abstract class Equipment {
	public Equipment(Chr me) {
		this.me = me;
		this.target = this::playerTarget;
		this.execute = this::execute;
		multi = Action.MULTI_DEFAULT_VALUE;
		rangeMin = Action.RANGE_MIN_DEFAULT_VALUE;
		rangeMax = Action.RANGE_MAX_DEFAULT_VALUE;
	}
	
	public Chr me;
	public Runnable target;
	public Runnable execute;
	public String name;
	
	// 攻撃の倍率
	public int multi;

	// 攻撃、回復等の範囲の最低値、最大値
	public int rangeMin;
	public int rangeMax;
	
	public abstract boolean playerTarget();
	public abstract void execute();
}
