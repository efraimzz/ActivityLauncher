package de.szalkowski.activitylauncher.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import de.szalkowski.activitylauncher.R
import de.szalkowski.activitylauncher.databinding.FragmentPackageListBinding
import javax.inject.Inject

@AndroidEntryPoint
class PackageListFragment : Fragment() {
    @Inject
    internal lateinit var packageListAdapter: PackageListAdapter

    private var _binding: FragmentPackageListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPackageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = activity as? ActionBarSearch
        packageListAdapter.filter = actionBar?.actionBarSearchText.orEmpty()
        actionBar?.onActionBarSearchListener = { search ->
            packageListAdapter.filter = search
        }

        packageListAdapter.onItemClick = {
            val action = PackageListFragmentDirections.actionSelectPackage(it.packageName)
            findNavController().navigate(action)
        }
        binding.rvPackages.adapter = packageListAdapter
        binding.rvPackages.isNestedScrollingEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
