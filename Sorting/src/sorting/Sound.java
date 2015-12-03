package sorting;

import javax.sound.sampled.*;
import java.util.concurrent.*;


public class Sound {

	public static final float SAMPLE_RATE = 8000f;
	public static final int CHANNELS = 1;
	public static final double VOLUME = 0.1;

	public static AudioFormat format;
	public static SourceDataLine line;
	public static ThreadPoolExecutor executor;

	public static void init() throws LineUnavailableException {
		format = new AudioFormat(SAMPLE_RATE, 8, CHANNELS, true, false);
		line = AudioSystem.getSourceDataLine(format);
		line.open(format);
		line.start();
		executor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}

	public static void play(int hz) {
		Runnable worker = new Runnable() {
			public void run() {
				byte[] buf = new byte[1];
				for(int i=0; i<(30) * 8; i++) {
					double angle = i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
					buf[0] = (byte)(Math.sin(angle) * 127.0 * (VOLUME * ((double)i / (30.0 * 8.0))));
					line.write(buf, 0, 1);
				}
			}
		};

		if(executor.getActiveCount() < 3) {
			executor.execute(worker);
		}
	}

}
