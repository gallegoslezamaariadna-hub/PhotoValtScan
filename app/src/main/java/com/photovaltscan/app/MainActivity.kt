package com.photovaltscan.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.photovaltscan.app.ui.screens.checklist.ChecklistScreen
import com.photovaltscan.app.ui.screens.dashboard.DashboardScreen
import com.photovaltscan.app.ui.screens.login.LoginScreen
import com.photovaltscan.app.ui.screens.register.RegisterScreen
import com.photovaltscan.app.ui.screens.project_detail.ProjectDetailScreen
import com.photovaltscan.app.ui.screens.survey.EquipmentSurveyScreen
import com.photovaltscan.app.ui.screens.findings.FindingsScreen
import com.photovaltscan.app.ui.screens.profile.ProfileScreen
import com.photovaltscan.app.ui.screens.admin.AdminDashboardScreen
import com.photovaltscan.app.ui.screens.support.SupportDashboardScreen
import com.photovaltscan.app.ui.screens.sync.SyncScreen
import com.photovaltscan.app.ui.screens.validation.ValidationScreen
import com.photovaltscan.app.ui.theme.PhotoValtScanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoValtScanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(
                                onLoginSuccess = {
                                    navController.navigate("dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onLoginSuccessAdmin = {
                                    navController.navigate("admin_dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onLoginSuccessSupport = {
                                    navController.navigate("support_dashboard") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                },
                                onNavigateToRegister = {
                                    navController.navigate("register")
                                }
                            )
                        }
                        composable("admin_dashboard") {
                            AdminDashboardScreen(
                                onNavigateToProfile = { navController.navigate("profile") },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        composable("support_dashboard") {
                            SupportDashboardScreen(
                                onNavigateToProfile = { navController.navigate("profile") },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        composable("register") {
                            RegisterScreen(
                                onRegisterSuccess = {
                                    navController.navigate("login") {
                                        popUpTo("register") { inclusive = true }
                                    }
                                },
                                onNavigateToLogin = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable("dashboard") {
                            DashboardScreen(
                                onProjectClick = { projectId ->
                                    navController.navigate("project_detail/$projectId")
                                },
                                onNavigateToProfile = { navController.navigate("profile") },
                                onNavigateToSync = { navController.navigate("sync") },
                                onNavigateToProjects = { navController.navigate("project_detail/PRJ-2023-089A") },
                                onNavigateToReports = { navController.navigate("validation") },
                                onLogout = {
                                    navController.navigate("login") {
                                        popUpTo(0)
                                    }
                                }
                            )
                        }
                        composable(
                            route = "project_detail/{projectId}",
                            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
                        ) {
                            ProjectDetailScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onStartChecklist = { projectId ->
                                    navController.navigate("survey")
                                }
                            )
                        }
                        composable(
                            route = "checklist/{projectId}",
                            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
                        ) {
                            ChecklistScreen(
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                        composable("survey") {
                            EquipmentSurveyScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onSaveSuccess = { 
                                    navController.navigate("findings")
                                }
                            )
                        }
                        composable("findings") {
                            FindingsScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onContinue = {
                                    navController.navigate("validation")
                                }
                            )
                        }
                        composable("validation") {
                            ValidationScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onSyncAndClose = { 
                                    navController.navigate("sync") 
                                }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onLogout = { 
                                    navController.navigate("login") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("sync") {
                            SyncScreen(
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToDashboard = {
                                    navController.navigate("dashboard") {
                                        popUpTo("dashboard") { inclusive = false }
                                    }
                                },
                                onNavigateToItem = { projectId ->
                                    navController.navigate("project_detail/$projectId")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
