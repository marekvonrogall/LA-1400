package marekvonrogall;
import robocode.*;

public class Metall extends JuniorRobot
{
public boolean scanned = false;
public int counter = 0;
	//If the opponent has been lost, the QuickScan scans a smaller radius to find the robot faster.
	public void quickScan() {
		if(scanned == true) {
			if(counter %2 == 0) {
				turnAheadRight(100, 20);
				turnGunTo(scannedAngle);
				turnGunRight(50);
				turnGunLeft(100);
			}
			else {
				turnAheadLeft(100, 20);
				turnGunTo(scannedAngle);
				turnGunLeft(50);
				turnGunRight(100);
			}
		}
	}
	public static double getBulletFlightTime(double distanceToEnemy, double bulletPower) {
    	return distanceToEnemy / bulletPower;
	}
	public void run() {
		setColors(white, red, white, white, black);
		while(true) {
			quickScan();
			//If no robots where found during the QuickScan, it does a full Scan
			scanned = false;
			if(counter %2 == 0) { turnGunRight(360); }
			else { turnGunLeft(360); }
			turnAheadLeft(90, 90);
			counter++;
		}
	}
	public void onScannedRobot() {
		double bulletFlightTime = getBulletFlightTime(scannedDistance, 3);
 	 	// Calculate enemy's position
 		double enemyX = robotX + scannedDistance * Math.sin(Math.toRadians(scannedBearing));
 		double enemyY = robotY + scannedDistance * Math.cos(Math.toRadians(scannedBearing));
		out.println("EnemyLocation (X|Y): " + enemyX + " | " + enemyY);
		//Enemy Location prediction (Q1)
		double predictedEnemyX = enemyX + ((scannedVelocity * 4) * bulletFlightTime * Math.sin(90 - scannedHeading));
		double predictedEnemyY = enemyY + ((scannedVelocity * 4) * bulletFlightTime * Math.cos(90 - scannedHeading));
		out.println("predictedEnemyX: " + predictedEnemyX);
		out.println("predictedEnemyY: " + predictedEnemyY);
		//Calculate enemy's predicted position into shooting angle (not implemented)
		//turnGunTo predicted shooting angle (not implemented)
		turnGunTo(scannedAngle);
		fire(3);
		scanned = true;
	}
}