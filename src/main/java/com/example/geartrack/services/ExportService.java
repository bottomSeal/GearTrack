package com.example.geartrack.services;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public interface ExportService {

    File getXLSXFile(UUID tripId) throws IOException;

    File getPDFFile(UUID tripId) throws IOException;
}
