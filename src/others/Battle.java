package others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import action.ActionMagic;
import chr.Chr;
import chr.Party;

public class Battle {
	public final int RESULT_NOTYET = 0;
	public final int RESULT_ALLY_RUN = 1;
	public final int RESULT_ENEMY_RUN = 2;
	public final int RESULT_ALLY_DESTROY = 3;
	public final int RESULT_ENEMY_DESTROY = 4;
	public final int RESULT_ALL_DEAD = 5;
	
	public int turn;
	
	public String[] allyName = {"のざわ", "とぐち", "さとう", "ふじた"};
	public String[] enemyName = {"みぎて", "おそざわ", "ひだりて"};
	// public Chr[] enemyChr = {new BossChr(), new BossChr(), new BossChr()};
	
	public Party ally;
	public Party enemy;
	
	public Battle() {
//		ally = new Party();
//		enemy = new Party();
//		ally.enemy = enemy;
//		enemy.enemy = ally;
	}
	
	public void init() {
//		MakeParty makeParty = new MakeParty();
//		ally.member = makeParty.makeParty(allyName, MakeParty.PARTY_KIND_ALLY);
//		enemy.member = makeParty.makeParty(enemyName, MakeParty.PARTY_KIND_ENEMY);

		ally = new Party(allyName, Party.PARTY_KIND_ALLY);
		
		enemy = new Party(enemyName, Party.PARTY_KIND_ENEMY);// 敵のジョブランダム選択
		// enemy = new Party(enemyName, Party.PARTY_KIND_ENEMY, enemyChr);
		
		ally.enemy = enemy;
		enemy.enemy = ally;
		

	}
	
	//int status;
	//final int SIPPUUDUKI;
	
	public int start() {
		System.out.println("まおうおそざわがあらわれた！");
		
		for (turn = 1;; turn++) {
			// ステータス表示
			IO.printStatus(ally.member);
			IO.printStatus(enemy.member);
			
			
			// 行動順決定
			Chr[] allList = addList(ally.member, enemy.member);// 行動選択はこちらを使う
			Chr[] orderList = makeOrder(allList);// 行動実行時はこちらを使う
			
			
			// 行動決定
			command(allList);
			
			
			// 行動実行
			int result = execute(orderList);
			
			
			// ゲーム継続判定
			switch (result) {
			case RESULT_NOTYET:
				break;
			// case RESULT_ALLY_RUN:
			//   break;
			// case RESULT_ENEMY_RUN:
			//   break;
			case RESULT_ALLY_DESTROY:
				if (ally.member.size() == 1) {
					System.out.printf("%sははいぼくした…", ally.member.get(0).name);
				} else {
					System.out.printf("%sたちはぜんめつした…", ally.member.get(0).name);
				}
				return result;
			case RESULT_ENEMY_DESTROY:
				if (enemy.member.size() == 1) {
					System.out.printf("%sをたおした!", enemy.member.get(0).name);
				} else {
					System.out.printf("%sたちをたおした!", enemy.member.get(0).name);
				}
				return result;
			case RESULT_ALL_DEAD:
				System.out.printf("全員死んだ!", enemy.member.get(0).name);
				return result;
			}
		}
	}
	
	public void action() {
		
	}
	
	// SPD決定に使う乱数(±X%）
	private final int ORDER_RANDOM_RANGE = 50;
	
	private Chr[] addList(ArrayList<Chr> ally, ArrayList<Chr> enemy) {
		// 味方敵含めたリストの作成
		ArrayList<Chr> tempList = new ArrayList<>();
		tempList.addAll(ally);
		tempList.addAll(enemy);
		
		Chr[] allList = new Chr[tempList.size()];
		for (int i = 0; i < tempList.size(); i++) {
			allList[i] = tempList.get(i);
		}
		return allList;
	}
	
	private Chr[] makeOrder(Chr[] allList) {
		// 加工したSPD順のリスト作成
		Chr[] orderList = new Chr[allList.length];
		for (int i = 0; i < allList.length; i++) {
			Chr c = allList[i];
			c.SPD = c.buffSPD + (int)(c.baseSPD * (100 - ORDER_RANDOM_RANGE + ORDER_RANDOM_RANGE * 2 * Math.random()) / 100);
			orderList[i] = c;
		}
		Arrays.sort(orderList, Comparator.comparing(Chr::getSPD).reversed());
		
		return orderList;
	}
	
	private void command(Chr[] allList) {
		for (Chr chr : allList) {
			if (chr.action != null) {
				chr.targets.clear();
			}
			chr.action = null;
			
			// 死んでたらスキップ
			if (chr.isDead()) {
				continue;
			}
			chr.command.run();
			chr.action.target.run();
		}
	}
	
	private int execute(Chr[] orderList) {
		for (Chr c : orderList) {
			// 死んでたらスキップ
			if (c.isDead())
				continue;
			
			// MPチェック　MPが足りなければスキップ
			if (c.action instanceof ActionMagic) {
				ActionMagic mAction = (ActionMagic) c.action;
				if (mAction.MPCons > c.MP) {
					IO.msgln("MPが足りない！");
					continue;
				}
			}
			
			// action実行
			c.action.execute();
			
			boolean allyDestroy = ally.isZenmetsu();
			boolean enemyDestroy = enemy.isZenmetsu();
			if (allyDestroy && enemyDestroy) {
				return RESULT_ALL_DEAD;
			} else if (allyDestroy) {
				return RESULT_ALLY_DESTROY;
			} else if (enemyDestroy) {
				return RESULT_ENEMY_DESTROY;
			}
		}
		return RESULT_NOTYET;
	}
	
}
