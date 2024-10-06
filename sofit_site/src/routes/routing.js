import React from "react";
import { createBrowserRouter } from "react-router-dom";
import Main from "../pages/Main";
import Layout from "../components/Layout";
import Login from "../pages/Login/Login.jsx";
import PostCreate from "../pages/Board/PostCreate.jsx";
import PostDetail from "../pages/Board/PostDetail.jsx";
import Board from "../pages/Board/Board.jsx";
import EditPost from "../pages/Board/EditPost.jsx";
import SignUp from "../pages/Login/Signup.jsx";
import Group from "../pages/Group/Group.jsx";
import GroupView from "../pages/Group/GroupView.jsx";
import GroupCreate from "../pages/Group/GroupCreate.jsx";
import GroupSetting from "../pages/Group/GroupManage/GroupSetting.jsx";
import EditProfile from "../pages/Login/EditProfile.jsx";
const router = createBrowserRouter([
  {
    element: <Layout />,
    children: [
      {
        path: "/",
        element: (
            <Main />
        ),
      },
      { path: "/editprofile", element: <EditProfile /> },
      { path: "/board", element: <Board/> },
      { path: "/board/create", element: <PostCreate /> },
      { path: "/board/:id", element: <PostDetail /> },
      { path: "/board/:id/edit", element: <EditPost /> },
      { path: "/groups", element: <Group /> },
      { path: "/groups/create", element: <GroupCreate /> },
      { path: "/groups/groupview/:id", element: <GroupView /> },
      { path: "/groups/groupview/:id/settings", element: <GroupSetting /> },// Add this route for WeeklyGoals
      { path: "/login", element: <Login /> },
      { path: "/signup", element: <SignUp /> },
    ],
  },
]);

export default router;
