package action.functions;

import action.Action;
import action.ActionBasicAttack;
import action.ActionFunction;
import action.magics.ActionMagicProtection;
import action.skills.ActionSkillHardDefense;
import action.skills.ActionSkillRush;
import chr.Chr;
import others.IO;

public class ActionFunctionToArot extends ActionFunction {

	public ActionFunctionToArot(Chr me) {
		super(me);
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	private final int A_ATTACK = 0;
	private final int A_RUSH = 1;
	private final int A_RUSH2 = 2;
	private final int A_PROTECTION = 3;
	private final int A_HARD_DEFENSE = 4;
	private final int A_TO_BROT = 5;
	
	
	public boolean playerTarget() {
		return true;
	}
	
	public void execute() {
		// アクションのリストを初期化
		me.actions.clear();
		// Aローテのアクション追加
		me.actions.add(new ActionBasicAttack(me));
		me.actions.add(new ActionSkillRush(me));
		me.actions.add(new ActionSkillRush(me));
		me.actions.add(new ActionMagicProtection(me));
		me.actions.add(new ActionSkillHardDefense(me));
		me.actions.add(new ActionFunctionToBrot(me));
		// アクションターン数を1追加
		me.actionTurn += IO.randomNum(1) + 1;
		
		setToNPC();
		IO.msgln("Aローテへ移行");
	}
	
	private void setToNPC() {
		me.command = this::nonPlayerCommand;
		me.actions.get(A_ATTACK).target = this::attackTarget;
		me.actions.get(A_RUSH).target = this::rushTarget;
		me.actions.get(A_RUSH2).target = this::rush2Target;
		me.actions.get(A_PROTECTION).target = this::protectionTarget;
		me.actions.get(A_HARD_DEFENSE).target = this::hardDefenseTarget;
		me.actions.get(A_TO_BROT).target = this::toBrotTarget;
	}
	
	public boolean nonPlayerCommand() {
		int actionNum = 0;
		while (true) {
			actionNum = IO.randomNum(A_TO_BROT);
			if (actionNum == A_PROTECTION && IO.checkStatus(Action.STATUS_DEF, me.party.member)) {
				continue;
			} else if (actionNum == A_HARD_DEFENSE && me.DEFNext != Action.DEF_NEXT_DEFAULT_VALUE) {
				continue;
			} else {
				break;
			}
		}
		
		me.action = me.actions.get(actionNum);
		return me.action.target.get();
	}
	
	public boolean attackTarget() {
		return IO.selectSingleRandomTarget(me.party.enemy.member, me);
	}
	
	public boolean rushTarget() {
		return IO.selectSingleRandomTarget(me.party.enemy.member, me);
	}
	
	public boolean rush2Target() {
		return IO.selectSingleRandomTarget(me.party.enemy.member, me);
	}
	
	public boolean protectionTarget() {
		me.targets.add(me);
		return true;
	}
	
	public boolean hardDefenseTarget() {
		me.DEFNext = me.actions.get(A_HARD_DEFENSE).multi;
		me.targets.clear();
		return true;
	}
	
	public boolean toBrotTarget() {
		me.targets.clear();
		return true;
	}
	
}
