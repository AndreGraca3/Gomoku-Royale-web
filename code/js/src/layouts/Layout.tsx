import { Outlet } from "react-router-dom";
import Header from "../components/Header/Header";
import Footer from "../components/Footer";
import { AuthnStatusProvider } from "../hooks/Auth/AuthnStatus";
import { Toaster } from "react-hot-toast";
import { SoundProvider } from "../hooks/Sound/Sound";

export default function Layout() {
  return (
    <AuthnStatusProvider>
      <SoundProvider>
        <div className="wrap min-h-screen flex flex-col">
          <div>
            <Toaster />
          </div>
          <Header />
          <div className="padding p-12" />
          <main className="grow container mx-auto w-screen h-full md:max-w-screen-xl px-6 flex flex-col overflow-hidden justify-center items-center">
            <Outlet />
          </main>
          <Footer />
        </div>
      </SoundProvider>
    </AuthnStatusProvider>
  );
}
