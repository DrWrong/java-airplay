package com.github.serezhka.airplay.player.h264dump;

import com.github.serezhka.airplay.lib.AudioStreamInfo;
import com.github.serezhka.airplay.lib.VideoStreamInfo;
import com.github.serezhka.airplay.server.AirPlayConsumer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class H264Dump implements AirPlayConsumer {

    private final FileChannel videoFileChannel;

    public H264Dump() throws IOException {
        videoFileChannel = FileChannel.open(Paths.get("dump.h264"),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    @Override
    public void onVideo(byte[] video) {
        try {
            videoFileChannel.write(ByteBuffer.wrap(video));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAudio(byte[] audio) {

    }

    @Override
    public void onVideoFormat(VideoStreamInfo videoStreamInfo) {
    }

    @Override
    public void onAudioFormat(AudioStreamInfo audioInfo) {
    }

    @PreDestroy
    public void save() throws IOException {
        videoFileChannel.close();
    }
}
