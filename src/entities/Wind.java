package entities;

import random.OpenSimplexNoise;

public class Wind {
	
	private boolean posOnly;
	
	private int windMod;
	private double lastWindSpeed;
	private double windSpeed;
	private double nextWindSpeed;
	
	private int interval = 10;
	private OpenSimplexNoise noise;
	private int noiseX = 0;
	
	public Wind(int windMod, long seed, boolean posOnly) {
		this.windMod = windMod;
		this.setPosOnly(posOnly);
		
		this.noise = new OpenSimplexNoise(seed);
	}
	
	public double getSpeed() {
		return windSpeed;
	}
	
	public void setSpeed(double newSpeed) {
		windSpeed = newSpeed;
	}
	
	public void nextSpeed() {
		
		if(noiseX % interval == 0 || noiseX == 0) {
			windSpeed = nextWindSpeed;
			lastWindSpeed = windSpeed;
			nextWindSpeed = noise.eval((int)noiseX/interval, 0) * windMod;
			if(posOnly) nextWindSpeed = Math.abs(nextWindSpeed);
		} else {
			windSpeed += (nextWindSpeed - lastWindSpeed)/interval;
		}
		
		noiseX ++;
	}

	public boolean isPosOnly() {
		return posOnly;
	}

	public void setPosOnly(boolean posOnly) {
		this.posOnly = posOnly;
	}

	public double getInterval() {
		return interval;
	}
	
	public void setInterval(int interval) {
		this.interval = interval;
	}

	public double getWindMod() {
		return windMod;
	}
	
	public void setWindMod(int strength) {
		windMod = strength;
	}
}
