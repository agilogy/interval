import bintray.Keys._

organization := "com.agilogy"

name := "interval"

version := "1.0.1"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.10.4","2.11.6")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

// --> Linters

// See tinyurl.com/sd15lint

// https://tpolecat.github.io/2014/04/11/scalac-flags.html
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",       // yes, this is 2 args
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

// Execute static analysis via `lint:compile`
val LintTarget = config("lint").extend(Compile)

inConfig(LintTarget) {

  Defaults.compileSettings ++
    Seq(
      sources in LintTarget := {
        val lintSources = (sources in LintTarget).value
        lintSources ++ (sources in Compile).value
      },
      scalacOptions in LintTarget ++= Seq(
        "-Xfatal-warnings",
        "-Ywarn-unused-import",
        "-Ywarn-dead-code",
        "-P:linter:disable:PreferIfToBooleanMatch"
      ),
      wartremoverErrors ++= Warts.allBut(Wart.DefaultArguments, Wart.MutableDataStructures)
    )
}

scalacOptions in Compile := (scalacOptions in Compile).value filterNot { switch =>
  switch.startsWith("-P:wartremover:") ||
    "^-Xplugin:.*/org[.]brianmckenna/.*wartremover.*[.]jar$".r.pattern.matcher(switch).find ||
    switch.startsWith("-P:linter:") ||
    "^-Xplugin:.*/com[.]foursquare[.]lint/.*linter.*[.]jar$".r.pattern.matcher(switch).find
}

resolvers += "Linter Repository" at "https://hairyfotr.github.io/linteRepo/releases"

addCompilerPlugin("com.foursquare.lint" %% "linter" % "0.1.9")

scalastyleFailOnError := true

// <-- Linters

// Reformat at every compile.
// See https://github.com/sbt/sbt-scalariform
scalariformSettings

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := "<empty>"

publishMavenStyle := false

// --> bintray

seq(bintrayPublishSettings:_*)

repository in bintray := "scala"

bintrayOrganization in bintray := Some("agilogy")

packageLabels in bintray := Seq("scala")

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

// <-- bintray
