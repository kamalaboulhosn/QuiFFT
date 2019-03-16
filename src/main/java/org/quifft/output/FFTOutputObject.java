package org.quifft.output;

import org.quifft.audioread.AudioReader;
import org.quifft.params.FFTParameters;

import javax.sound.sampled.AudioFormat;

public abstract class FFTOutputObject {

    /**
     * Name of file for which the FFT operation was performed
     */
    public String fileName;

    /**
     * Duration of input audio file in milliseconds
     */
    public long fileDurationMs;

    /**
     * Frequency resolution of FFT result
     * <p>This is calculated by dividing the audio file's sampling rate by the number of points in the FFT.
     * A lower frequency resolution makes it easier to distinguish between frequencies that are close together.
     * By improving frequency resolution, however, some time resolution is lost because there are fewer
     * FFT calculations per unit of time (sampling windows are larger).</p>
     */
    public double frequencyResolution;

    /**
     * Length of each sampling window in milliseconds
     * <p>This is proportional to the length of each window in terms of number of samples.</p>
     */
    public long windowDurationMs;

    /**
     * The parameters used to compute this FFT
     */
    public FFTParameters fftParameters;

    /**
     * Sets metadata to be returned by an output object ({@link FFTResult} or {@link FFTStream})
     * @param reader AudioReader created for input file
     * @param params parameters for FFT
     */
    public void setMetadata(AudioReader reader, FFTParameters params) {
        this.fileName = reader.getFile().getName();

        this.fileDurationMs = reader.getFileDurationMs();

        AudioFormat format = reader.getAudioFormat();
        this.frequencyResolution = reader.getAudioFormat().getSampleRate() / params.windowSize;

        double sampleLengthMs = 1 / format.getSampleRate() * 1000;
        this.windowDurationMs = Math.round(sampleLengthMs * params.windowSize);

        this.fftParameters = params;
    }
}