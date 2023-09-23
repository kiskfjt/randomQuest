package action;

import chr.Chr;

public abstract class Action {
	public Runnable target;
	public String name;
	public abstract void playerTarget();
	public abstract void execute();
	public Chr me;
	public int multi;
	public Action(Chr me) {
		this.me = me;
		this.target = this::playerTarget;
	}
}
