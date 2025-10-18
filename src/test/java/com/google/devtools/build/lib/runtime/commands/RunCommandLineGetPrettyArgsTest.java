package com.google.devtools.build.lib.runtime.commands;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.devtools.build.lib.vfs.DigestHashFunction;
import com.google.devtools.build.lib.vfs.FileSystem;
import com.google.devtools.build.lib.vfs.Path;
import com.google.devtools.build.lib.vfs.inmemoryfs.InMemoryFileSystem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class RunCommandLineGetPrettyArgsTest {
  private final FileSystem fs = new InMemoryFileSystem(DigestHashFunction.SHA256);
  private final Path workingDir = fs.getPath("/workingdir");

  @Test
  public void testGetPrettyArgs() {
    RunCommandLine commandLine =
        new RunCommandLine.Builder(
                ImmutableSortedMap.of(),
                ImmutableSortedSet.of(),
                workingDir,
                /* isTestTarget= */ false,
                /* showRunArgs= */ false)
            .addArg("arg1")
            .addArg("arg2")
            .addArgsFromResidue(ImmutableList.of("residue1", "residue2"))
            .build();
    assertThat(commandLine.getPrettyArgs()).isEqualTo("arg1 arg2 <args omitted>");
  }

  @Test
  public void testGetPrettyArgsWithShowRunArgs() {
    RunCommandLine commandLine =
        new RunCommandLine.Builder(
                ImmutableSortedMap.of(),
                ImmutableSortedSet.of(),
                workingDir,
                /* isTestTarget= */ false,
                /* showRunArgs= */ true)
            .addArg("arg1")
            .addArg("arg2")
            .addArgsFromResidue(ImmutableList.of("residue1", "residue2"))
            .build();
    assertThat(commandLine.getPrettyArgs()).isEqualTo("arg1 arg2 residue1 residue2");
  }
}