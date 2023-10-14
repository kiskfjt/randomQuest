package item;

import action.Action;
import chr.Chr;

public abstract class Item {
	public Item(Chr me) {
		this.me = me;
		this.target = this::playerTarget;
		this.execute = this::execute;
	}
	
	public Chr me;
	public Runnable target;
	public Runnable execute;
	public Action action;
	public String name;
	
	// 攻撃の倍率
	public int multi;
	public static final int MULTI_DEFAULT_VALUE = 100;

	// 攻撃、回復等の範囲の最低値、最大値
	public int rangeMin;
	public int rangeMax;
	public static final int RANGE_DEFAULT_VALUE = 100;
	
	public abstract void playerTarget();
	public abstract void execute();
}
