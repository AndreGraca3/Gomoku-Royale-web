import { Outlet } from "react-router-dom";
import Header from "../components/Header/Header";
import Footer from "../components/Footer";

export default function Layout() {
  return (
    <div>
      <Header />
      <div className="padding p-12" />
      <main className="m-auto block">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
}
