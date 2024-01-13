package action.functions;

import action.ActionFunction;
import action.magics.ActionMagicBreakShield;
import action.skills.ActionSkillFireBreath;
import action.skills.ActionSkillKiaitame;
import action.skills.ActionSkillMawashigeri;
import action.skills.ActionSkillShippu;
import chr.Chr;
import chr.bosses.BossMortamor2nd;
import others.IO;

public class ActionFunctionToBrot extends ActionFunction {
	
	public ActionFunctionToBrot(Chr me) {
		super(me);
	}
	
	private final int A_KIAITAME = 0;
	private final int A_BREAK_SHIELD = 1;
	private final int A_SHIPPU = 2;
	private final int A_MAWASHIGERI = 3;
	private final int A_FIREBREATH = 4;
	private final int A_TO_AROT = 5;
	
	
	public boolean playerTarget() {
		return true;
	}
	
	public void execute() {
		// アクションのリストを初期化
		me.actions.clear();
		// Bローテのアクション追加
		me.actions.add(new ActionSkillKiaitame(me));
		me.actions.add(new ActionMagicBreakShield(me));
		me.actions.add(new ActionSkillShippu(me));
		me.actions.add(new ActionSkillMawashigeri(me));
		me.actions.add(new ActionSkillFireBreath(me));
		me.actions.add(new ActionFunctionToArot(me));
		// アクションターン数を1追加
		me.actionTurn++;
		// Bローテの番号を初期化
		((BossMortamor2nd)me).BrotNo = 0;
		
		setToNPC();
		IO.msgln("Bローテへ移行");
	}
	
	private void setToNPC() {
		me.command = me::nonPlayerCommand;
		me.actions.get(A_KIAITAME).target = this::kiaitameTarget;
		me.actions.get(A_BREAK_SHIELD).target = this::breakShieldTarget;
		me.actions.get(A_SHIPPU).target = this::shippuTarget;
		me.actions.get(A_MAWASHIGERI).target = this::mawashigeriTarget;
		me.actions.get(A_FIREBREATH).target = this::fireBreathTarget;
		me.actions.get(A_TO_AROT).target = this::toArotTarget;
	}
	
	public boolean nonPlayerCommand() {
		int actionNum = 0;
		while (true) {
			actionNum = ((BossMortamor2nd)me).BrotNo * 2 + IO.randomNum(1);
			if (actionNum == A_BREAK_SHIELD && IO.checkBuff(STATUS_DEF, me.party.enemy.member)) {
				continue;
			} else {
				break;
			}
		}
		IO.msgln("Bローテのアクション番号=" + actionNum);
		
		((BossMortamor2nd)me).BrotNo++;		
		me.action = me.actions.get(actionNum);
		return me.action.target.get();
	}
	
	public boolean kiaitameTarget() {
		me.targets.clear();
		return true;
	}
	
	public boolean breakShieldTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}
	
	public boolean shippuTarget() {
		
		return IO.selectSingleRandomTarget(me.party.enemy.member, me);
	}
	
	public boolean mawashigeriTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}
	
	public boolean fireBreathTarget() {
		return IO.selectAllTargets(me.party.enemy.member, me);
	}
	
	public boolean toArotTarget() {
		me.targets.clear();
		return true;
	}
	
}
