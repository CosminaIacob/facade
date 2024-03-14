package design.patterns.facade;

import design.patterns.media_library.AudioMixer;
import design.patterns.media_library.BitrateReader;
import design.patterns.media_library.Codec;
import design.patterns.media_library.CodecFactory;
import design.patterns.media_library.MPEG4CompressionCodec;
import design.patterns.media_library.OggCompressionCodec;
import design.patterns.media_library.VideoFile;

import java.io.File;

public class VideoConversionFacade {

    public File convertVideo(String fileName, String format) {
        System.out.println("VideoConversionFacade: conversion started.");

        VideoFile file = new VideoFile(fileName);
        Codec sourceCodec = CodecFactory.extract(file);
        Codec destinationCodec;
        if (format.equals("mp4")) {
            destinationCodec = new MPEG4CompressionCodec();
        } else {
            destinationCodec = new OggCompressionCodec();
        }

        VideoFile buffer = BitrateReader.read(file, sourceCodec);
        VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
        File result = (new AudioMixer()).fix(intermediateResult);
        System.out.println("VideoConversionFacade: conversion completed");
        return result;
    }
}
