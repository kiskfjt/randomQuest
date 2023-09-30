package action;

import chr.Chr;
import others.IO;

public class ActionBasicGuard extends ActionBasic {
	public ActionBasicGuard(Chr me) {
		super(me);
		name = "ぼうぎょ";
		me.DEFMulti = 2;
	}

	public void playerTarget() {
		// ターゲット選択はなし
	}

	public void execute() {
		IO.msgln("【%sは身をかためている！】", me.name);
	}

}
