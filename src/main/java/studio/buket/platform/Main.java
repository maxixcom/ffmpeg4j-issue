package studio.buket.platform;

import com.github.manevolent.ffmpeg4j.FFmpegIO;
import com.github.manevolent.ffmpeg4j.stream.output.FFmpegTargetStream;
import com.github.manevolent.ffmpeg4j.stream.source.FFmpegSourceStream;
import com.github.manevolent.ffmpeg4j.transcoder.Transcoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

public class Main {
    private void transcode(InputStream inputStream,
                           String inputFormatName,
                           SeekableByteChannel outputChannel,
                           String outputFormatName) {
        try (FFmpegSourceStream sourceStream = FFmpegIO.openInputStream(inputStream).open(inputFormatName);
             FFmpegTargetStream targetStream = FFmpegIO.openChannel(outputChannel).asOutput().open(outputFormatName)) {
            sourceStream.registerStreams();
            sourceStream.copyToTargetStream(targetStream);

            Transcoder.convert(sourceStream, targetStream, Double.MAX_VALUE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void run() throws IOException {
        Set<OpenOption> openOptions = Set.of(
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
        try (
                FileInputStream inputStream = new FileInputStream("tmp/sample-0.mp4");
                SeekableByteChannel seekableByteChannel = Files.newByteChannel(Paths.get("tmp/output.ts"), openOptions);
        ) {
            transcode(inputStream,"mp4", seekableByteChannel, "mpegts");
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}