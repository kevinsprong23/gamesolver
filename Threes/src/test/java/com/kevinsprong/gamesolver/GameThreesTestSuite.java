package com.kevinsprong.gamesolver;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.kevinsprong.gamesolver.*;

// test suite to run game 2048 tests
@RunWith(Suite.class)
@SuiteClasses({BoardUpdateTest.class, WinConditionTest.class, 
	LegalMoveTest.class, AlphaBetaThreesTest.class})
public class GameThreesTestSuite {
	@BeforeClass
	public static void setUpClass() throws Exception {
	}
	
	@AfterClass
	public static void tearDownClass() throws Exception {
	}
}
