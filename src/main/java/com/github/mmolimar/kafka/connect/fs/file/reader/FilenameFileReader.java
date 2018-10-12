package com.github.mmolimar.kafka.connect.fs.file.reader;

import com.github.mmolimar.kafka.connect.fs.file.Offset;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

public class FilenameFileReader extends AbstractFileReader<FilenameFileReader.FilenameRecord> {
    private static final Logger log = LoggerFactory.getLogger(FilenameFileReader.class);

    private final FilenameOffset offset;
    private String stringFilePath;
    private Schema schema;

    public FilenameFileReader(FileSystem fs, Path filePath, Map<String, Object> config) throws IOException {
        super(fs, filePath, new FilenameToStruct(), config);
        this.stringFilePath = filePath.toString();

        this.offset = new FilenameOffset(0);
        this.schema = SchemaBuilder.struct().field("filename", Schema.STRING_SCHEMA).build();
    }

    @Override
    protected FilenameRecord nextRecord() {
        if (!hasNext()) {
            throw new NoSuchElementException("There are no more records in file: " + getFilePath());
        }
        return new FilenameRecord(schema, stringFilePath);
    }

    @Override
    protected void configure(Map<String, Object> config) { }

    // Return whether or not there is a next line in the file.
    // Since we are not reading the file, we will always return false. No more line within the file.
    @Override
    public boolean hasNext() {
        if (this.offset.getRecordOffset() == 0) {
            this.offset.setOffset(this.offset.getRecordOffset() + 1);
            return true;
        } else if (this.offset.getRecordOffset() == 1) {
            // return true but not adding the record offset because then it will become 2 which throws an exception.
            return true;
        } else {
            return false;
        }
    }

    // Seek to a particular line within the file.
    // Since we are not reading the file, this method is not being used.
    @Override
    public void seek(Offset offset) {
        log.info("seek() to offset " + String.valueOf(offset.getRecordOffset()));
        if (offset.getRecordOffset() < 0) {
            throw new IllegalArgumentException("Record offset must be greater than 0");
        }
        this.offset.setOffset(offset.getRecordOffset());
    }

    @Override
    public FilenameOffset currentOffset() {
        return offset;
    }

    @Override
    public void close() throws IOException { }

    public static class FilenameOffset implements Offset {
        private long offset;

        public FilenameOffset(long offset) {
            this.offset = offset;
        }

        public void setOffset(long offset) {
            this.offset = offset;
        }

        @Override
        public long getRecordOffset() {
            return offset;
        }
    }

    static class FilenameToStruct implements ReaderAdapter<FilenameRecord> {

        @Override
        public Struct apply(FilenameRecord record) {
            return new Struct(record.schema).put(record.schema.fields().get(0), record.value);
        }
    }

    static class FilenameRecord {
        private final Schema schema;
        private final String value;

        public FilenameRecord(Schema schema, String value) {
            this.schema = schema;
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
