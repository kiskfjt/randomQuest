package action;

import chr.Chr;
import others.IO;

public class ActionBasicGuard extends ActionBasic {
	public ActionBasicGuard(Chr me) {
		super(me);
		name = "ぼうぎょ";
	}

	public boolean playerTarget() {
		me.DEFNext = 2.0;
		return true;
		// ターゲット選択はなし
	}

	public void execute() {
		IO.msgln("【%sは身をかためている！】", me.name);
	}

}
