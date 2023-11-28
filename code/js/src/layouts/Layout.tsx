import { Outlet } from "react-router-dom";
import Header from "../components/Header/Header";
import Footer from "../components/Footer";
import { AuthnStatusProvider } from "../hooks/Auth/AuthnStatus";

export default function Layout() {
  return (
    <AuthnStatusProvider>
      <div className="wrap min-h-screen flex flex-col">
        <Header />
        <div className="padding p-12" />
        <main className="grow container mx-auto w-screen h-full md:max-w-screen-xl px-6 flex flex-col overflow-hidden justify-center items-center">
          <Outlet />
        </main>
        <Footer />
      </div>
    </AuthnStatusProvider>
  );
}
