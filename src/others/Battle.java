package others;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
	// public String[] enemyName = {"みぎて", "おそざわ", "ひだりて"};
	// public Chr[] enemyChr = {new BossChr(), new BossChr(), new BossChr()};
	
	public Party ally;
	public Party enemy;
	
	public Battle() {
//		ally = new Party();
//		enemy = new Party();
//		ally.enemy = enemy;
//		enemy.enemy = ally;
	}
	
	public void init(String[] enemyName, Chr[] enemyChr) {
//		MakeParty makeParty = new MakeParty();
//		ally.member = makeParty.makeParty(allyName, MakeParty.PARTY_KIND_ALLY);
//		enemy.member = makeParty.makeParty(enemyName, MakeParty.PARTY_KIND_ENEMY);

		ally = new Party(allyName, Party.PARTY_KIND_ALLY);
		
		// enemy = new Party(enemyName, Party.PARTY_KIND_ENEMY);// 敵のジョブランダム選択
		enemy = new Party(enemyName, Party.PARTY_KIND_ENEMY, enemyChr);// 敵のジョブ指定版
		
		ally.enemy = enemy;
		enemy.enemy = ally;
		

	}
	
	//int status;
	//final int SIPPUUDUKI;
	
	public int start() {
		System.out.println("【まおうおそざわがあらわれた！】");
		
		for (turn = 1;; turn++) {
			// ステータス表示
			IO.printStatus(ally.member);
			IO.printStatus(enemy.member);
			
			// 敵味方配列作成
			Chr[] allList = addList(ally.member, enemy.member);// 行動選択はこちらを使う
			
			// 行動ターン数初期化、1回のみのバフ初期化
			setActionTurn(allList);
			
			// 行動決定
			command(allList);
			
			// 行動順決定
			Chr[] orderList = makeOrder(allList);// 行動実行時はこちらを使う
			
			// 行動実行
			int result = execute(orderList, allList);
			
			IO.ln();
			
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
					IO.msgln("%sははいぼくした…", ally.member.get(0).name);
				} else {
					IO.msgln("%sたちはぜんめつした…", ally.member.get(0).name);
				}
				IO.msgln("**********************");
				IO.msgln("%10s", "ゲームオーバー！");
				IO.msgln("**********************");
				return result;
			case RESULT_ENEMY_DESTROY:
				if (enemy.member.size() == 1) {
					IO.msgln("%sをたおした!", enemy.member.get(0).name);
				} else {
					IO.msgln("%sたちをたおした!", enemy.member.get(1).name);
				}
				IO.msgln("**********************");
				IO.msgln("%10s", "ゲームクリア！");
				IO.msgln("**********************");
				return result;
			case RESULT_ALL_DEAD:
				IO.msgln("全員死んだ!", enemy.member.get(0).name);
				IO.msgln("**********************");
				IO.msgln("%10s", "ゲームオーバー！");
				IO.msgln("**********************");
				return result;
			}
		}
	}
	
	public void action() {
		
	}
	
	// SPD決定に使う乱数(±X%）
	private final double SPD_RANGE_MIN = 0.5;
	private final double SPD_RANGE_MAX = 1.5;
	
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
			c.SPD = c.buffSPD + c.SPDNext + (int) (c.baseSPD * IO.randomNum(SPD_RANGE_MIN, SPD_RANGE_MAX));
			orderList[i] = c;
		}
		Arrays.sort(orderList, Comparator.comparing(Chr::getSPD).reversed());
		
		return orderList;
	}
	
	
	private void setActionTurn(Chr[] allList) {
		for (Chr chr : allList) {
			chr.actionTurn = chr.actionTurnDefault;
			chr.DEFNext = chr.DEF_MULTI_DEFAULT;
			chr.SPDNext = chr.SPD_NEXT_DEFAULT;
		}
	}
	
//	private void resetATKMulti(Chr chr) {
//		if (chr.attackedFlg) {
//			chr.ATKNext = chr.ATK_MULTI_DEFAULT;
//			chr.attackedFlg = false;
//		}
//	}
	
	
	private void command(Chr[] allList) {
		boolean isBack = false;// 戻るコマンドを使用したかどうか
		for (int i = 0; i < allList.length; i++) {
			Chr chr = allList[i];
			// ターゲットとコマンドを前ターンのままにしたい場合はスキップ
			if (IO.checkStatusBeforeCommand(chr)) {
				continue;
			} else {
				if (chr.action != null) {
					chr.targets.clear();
				}
				chr.action = null;

				// 死んでたらスキップ
				if (chr.isDead()) {
					if (i > 0 && isBack) {
						i -= 2;
						continue;
					} else {
						continue;
					}
				}

				isBack = !chr.command.get();
				if (isBack) {
					if (i == 0) {
						i--;// PCActionがセットされていない、かつ先頭キャラの場合はもう一度コマンド選択する
					} else {
						i -= 2;// PCActionがセットされていない、かつ先頭キャラでない場合は1つ前のキャラのコマンド選択に戻る
					}
				}
			}
		}
	}
	
	private void command(Chr chr) {
		Chr[] array = {chr};
		command(array);
	}
	
	private int execute(Chr[] orderList, Chr[] allList) {
		for (Chr c : orderList) {
			// 死んでたらスキップ
			if (c.isDead())
				continue;
			
			// 行動前状態異常の影響実行
			if (c.status != 0) {
				if (IO.doStatusEffectBeforeAction(c, allList)) {
					continue;
				}
			}
			
			// アクションが設定されていない、もしくは蘇生されたばかりの場合もスキップ
			if (c.action == null)
				continue;

			// MPチェック　MPが足りなければスキップ
			if (c.action.MPCons > c.MP) {
				IO.msgln("MPが足りない！");
				continue;
			}

			// action実行
			while (true) {
				c.action.execute();
				IO.enter();// アクション毎にエンターの入力を待つ
				c.actionTurn--;
				
				//resetATKMulti(c);
				
				if (c.actionTurn == 0) {
					break;
				} else {
					boolean allyDestroy = ally.isZenmetsu();
					boolean enemyDestroy = enemy.isZenmetsu();
					if (allyDestroy || enemyDestroy) {
						break;
					} else {
						command(c);
					}
				}
			}
			
			// 行動後状態異常の影響実行
			if (c.status != 0) {
				IO.doStatusEffectAfterAction(c);
			}
			
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
		
		// 全員の行動が終わった後に状態異常の継続ターン数が0の場合、状態異常を解除する
		for (Chr c : allList) {
			IO.recoverFromAbnormalStatus(c);
		}
		
		// 全員の行動が終わった後に状態異常以外の継続ターンを1減らす
		for (Chr c : allList) {
			IO.decrementEffectTurns(c);
		}
		
		return RESULT_NOTYET;
	}
	
}
